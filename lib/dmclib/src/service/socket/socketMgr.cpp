////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include <cassert>
#include <iostream>
#include "serverSocket.h"
#include "socketMgr.h"
#include <sstream>

#ifdef __linux__
	#include <cstring>
	#define INVALID_SOCKET -1
	#define SOCKET_ERROR -1
	#define closesocket(SOCKET) close(SOCKET)
#endif // __linux__

using namespace std;

namespace dmc {

	void initSocketLib();

	//------------------------------------------------------------------------------------------------------------------
	SocketMgr* SocketMgr::sInstance = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::init(unsigned _port) {
		assert(!sInstance);
		sInstance = new SocketMgr(_port);
	}

	//------------------------------------------------------------------------------------------------------------------
	SocketMgr* SocketMgr::get() {
		assert(sInstance);
		return sInstance;
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::update() {
		cleanDeadConnections();
	}

	//------------------------------------------------------------------------------------------------------------------
	SocketMgr::SocketMgr(unsigned _port)
		:mPort(_port)
		,mOnNewConnection([](unsigned){}) // Default empty delegate
	{
		// Start listening thread
		assert(0 != _port);	// Ensure data integrity
		initSocketLib();

		addrinfo * socketAddress = buildAddresInfo(_port);
		mListener = socket(socketAddress->ai_family, socketAddress->ai_socktype, socketAddress->ai_protocol);
		//delete socketAddress;
		assert(mListener != INVALID_SOCKET);

		startListening(socketAddress);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool SocketMgr::write(unsigned _clientId, const std::string& _msg) const {
		// Is there such client between active connections?
		mConMutex.lock();
		auto connectionIter = mActiveConnections.find(_clientId);
		if(connectionIter == mActiveConnections.end()) {
			mConMutex.unlock();
			std::cout << "Warning: Trying to write to inexistent client " << _clientId << "\n";
			return false; // No client known by that id
		}

		bool ret = connectionIter->second->write(_msg);
		mConMutex.unlock();
		return ret;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool SocketMgr::readAny(unsigned& _clientId, std::string& _msg) const {
		mConMutex.lock();
		for(auto& connection : mActiveConnections) {
			if(connection.second->read(_msg)) { // Message received
				_clientId = connection.first;
				mConMutex.unlock();
				return true;
			}
		}
		// No message received
		mConMutex.unlock();
		return false;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool SocketMgr::readFrom(unsigned _clientId, std::string& _msg) const {
		mConMutex.lock();
		// Is there such client between active connections?
		auto connectionIter = mActiveConnections.find(_clientId);
		if(connectionIter == mActiveConnections.end()) {
			mConMutex.unlock();
			return false; // No client known by that id
		}

		bool ret = connectionIter->second->read(_msg);
		mConMutex.unlock();
		return ret;
	}

	//------------------------------------------------------------------------------------------------------------------
	addrinfo* SocketMgr::buildAddresInfo(unsigned _port) {
		// Translate port into a string
		assert(0 != _port);
		std::stringstream portName;
		portName << _port;

		// Get address information
		struct addrinfo intendedAddress;
		memset(&intendedAddress, 0, sizeof(struct addrinfo));
		intendedAddress.ai_flags = AI_PASSIVE;	// Socket address will be used to call the bind function
		intendedAddress.ai_family = AF_INET;
		intendedAddress.ai_socktype = SOCK_STREAM;
		intendedAddress.ai_protocol = IPPROTO_TCP;

		struct addrinfo *socketAddress = nullptr;
		int res = getaddrinfo(nullptr, portName.str().c_str(), &intendedAddress, &socketAddress);
		assert(res == 0);
		assert(nullptr != socketAddress);

		return socketAddress;
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::startListening(const addrinfo* _socketAddress) {
		// Setup the TCP listening socket
		int res = ::bind( mListener, _socketAddress->ai_addr, (int)_socketAddress->ai_addrlen);
		assert(res != SOCKET_ERROR);
		res = listen(mListener, SOMAXCONN);
		assert(res != SOCKET_ERROR);
		std::cout << "Server is listening\n";

		// Start listening thread
		mListenThread = std::thread([this](){
			for(;;) {
				SOCKET conn = accept(mListener, nullptr, nullptr);
				if (INVALID_SOCKET == conn) {
					closesocket(mListener);
					assert(false);
					return;
				} else {
					std::cout << "Accepting connection on socket " << conn << "\n";
					createConnection(conn);
				}
			}
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::createConnection(int _socketDescriptor) {
		// Ensure descriptor isn't already in use
		mConMutex.lock();
		assert(mActiveConnections.find(_socketDescriptor) == mActiveConnections.end());
		// Add new connection
		mActiveConnections[_socketDescriptor] = new ServerSocket(_socketDescriptor);
		mConMutex.unlock();
		mOnNewConnection(_socketDescriptor); // Invoke delegate
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::cleanDeadConnections() {
		for(auto deadConn : mDeadConnections)
			delete deadConn;
		mDeadConnections.clear();
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::ownConnection(unsigned _clientId) {
		// Is there such client between active connections?
		mConMutex.lock();
		auto connectionIter = mActiveConnections.find(_clientId);
		if(connectionIter == mActiveConnections.end()) {
			mConMutex.unlock();
			cout << "Error trying to lock a connection: Connection " << _clientId << " not found in SocketMgr\n";
			assert(false);
			return;
		}

		connectionIter->second->own();
		mConMutex.unlock();
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::releaseConnection(unsigned _clientId) {
		// Is there such client between active connections?
		mConMutex.lock();
		auto connectionIter = mActiveConnections.find(_clientId);
		if(connectionIter == mActiveConnections.end()) {
			mConMutex.unlock();
			cout << "Error trying to unlock a connection: Connection " << _clientId << " not found in SocketMgr\n";
			assert(false);
			return; // No client known by that id
		}

		connectionIter->second->release();
		mConMutex.unlock();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool SocketMgr::closeConnection(unsigned _clientId) {
		// Is there such client between active connections?
		mConMutex.lock();
		auto connectionIter = mActiveConnections.find(_clientId);
		if(connectionIter == mActiveConnections.end()) {
			mConMutex.unlock();
			return false; // No client known by that id
		}

		ServerSocket* closedConn = connectionIter->second;
		mDeadConnections.push_back(closedConn); // Ensure connections only get closed by the main thread
		mActiveConnections.erase(connectionIter); // Remove it from the list of active connections
		mConMutex.unlock();
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool SocketMgr::isConnectionAlive(unsigned _clientId) const {
		mConMutex.lock();
		bool isAlive = mActiveConnections.find(_clientId) != mActiveConnections.end();
		mConMutex.unlock();
		return isAlive;
	}

	//------------------------------------------------------------------------------------------------------------------
	void SocketMgr::onNewConnection(std::function<void(unsigned)> _delegate) {
		mOnNewConnection = _delegate;
	}

	//------------------------------------------------------------------------------------------------------------------
	void initSocketLib() {
#ifdef _WIN32
		WSADATA wsaData;

		// Initialize Winsock
		int iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
		assert(!iResult);
#endif // _WIN32
	}
}