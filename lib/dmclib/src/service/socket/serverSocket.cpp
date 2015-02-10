////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/23
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include <cassert>
#include <iostream>
#include "serverSocket.h"
#include "socketMgr.h"

#ifdef _WIN32
	#include <winsock2.h>
	#include <ws2tcpip.h>
#endif // _WIN32
#ifdef __linux__
	#include <unistd.h>
	#include <sys/types.h>
	#include <sys/socket.h>
	#include <netdb.h>
	#define SOCKET_ERROR -1
#endif // __linux__

const unsigned BUFFER_SIZE = 1024;

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	ServerSocket::ServerSocket(int _socketDescriptor) 
		:mSocket(_socketDescriptor)
	{
		mListenThread = std::thread([this](){
			listen();
			SocketMgr::get()->closeConnection(mSocket);
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	ServerSocket::~ServerSocket() {
		assert(mListenThread.get_id() != std::this_thread::get_id()); // Ensure it's not this thread trying to delete itself
		mMustClose = true;
		assert(mListenThread.joinable());
		mListenThread.join();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool ServerSocket::write(const std::string& _msg) {
		return send(mSocket, _msg.c_str(), _msg.size(), 0) != SOCKET_ERROR;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool ServerSocket::read(std::string& _msg) {
		mReadLock.lock(); // Prevent collisions

		bool canRead = !mInBuffer.empty();
		if(canRead) {
			_msg = mInBuffer; // Note: In case is necessary, this copy can be avoided by using move semantics and recreating the string with placement new.
			mInBuffer.clear();
		}

		mReadLock.unlock();

		return canRead;
	}

	//------------------------------------------------------------------------------------------------------------------
	void ServerSocket::listen() {
		char listenBuffer[BUFFER_SIZE];

		// Query message
		for(;;) {
			if(mMustClose) // Allow proper closing of the connection
				return;

			int nBytes = recv(mSocket, listenBuffer, BUFFER_SIZE, 0);
			if(nBytes > 0) { // Incomming message
				mReadLock.lock(); // Prevent collisions

				//for(int i = 0; i < nBytes; ++i)
					mInBuffer.append(listenBuffer, nBytes);

				mReadLock.unlock(); // 
			} 
			else if(nBytes == 0) { // Connection properly closed
				std::cout << "Connection " << mSocket << " closed properly\n";
				return;
			} 
			else {
				std::cout << "Connection closed unexpectedly on socket " << mSocket << "\n"; // Something went wrong on the other side
				return;
			}
		}
	}

} // namespace dmc
