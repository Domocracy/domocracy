//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2014-Dec-21
//----------------------------------------------------------------------------------------------------------------------
// Generic socket
#include "socket.h"

#include <cassert>
#include <fcntl.h>
#include <iostream>
#include <sstream>

#include <thread>
#include <cstring> // memset
#include <string>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	Socket::Socket(SocketDesc _desc)
		:mSocket(_desc)
	{
	}

	//------------------------------------------------------------------------------------------------------------------
	Socket::~Socket() {
		close();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::connectTo(const std::string& _url, unsigned _port, Protocol _protocol) {
		mMustClose = false;
		std::stringstream portStream;
		portStream << _port;

		// ------ Get proper address info -------
		struct addrinfo addrHints;
		struct addrinfo* address = nullptr;
		memset(&addrHints, 0, sizeof(addrHints));
		addrHints.ai_family = AF_UNSPEC;		// Connect to either ip v4 or v6
		assert(_protocol == Protocol::TCP || _protocol == Protocol::UDP); // Only TCP is currently supported
		addrHints.ai_socktype = SOCK_STREAM;	// Connection type TCP IP
		switch(_protocol) {
		case Protocol::TCP:
			addrHints.ai_protocol = IPPROTO_TCP;
			break;
		case Protocol::UDP:
			addrHints.ai_protocol = IPPROTO_UDP;
			break;
		default:
			assert(false);
			break;
		}

		int res = getaddrinfo(_url.c_str(), portStream.str().c_str(), &addrHints, &address);
		if(0 != res)
		{
			std::cout << "Error: unable to get addr info for socket\n\t-url: "
				<< _url << "\n\t-port: " << portStream.str() << "\n";
#ifdef _WIN32
			std::cout << std::string(gai_strerrorA(res)) << "\n";
#endif // _WIN32
			return false;
		}

		// ----- Try to connect -----
		for(auto addr=address; nullptr != addr; addr = addr->ai_next) {
			mSocket = socket(addr->ai_family,
									   addr->ai_socktype,
									   addr->ai_protocol);
			if (mSocket == INVALID_SOCKET) {

				std::cout << "socket failed with error: "
#ifdef _WIN32
					<<  WSAGetLastError()
#endif // _WIN32
					<< "\n";
				return false;
			}

			//fcntl(mSocket, F_SETFL, O_NONBLOCK);
			// Connect to server.
			if(SOCKET_ERROR != connect( mSocket, addr->ai_addr, (int)addr->ai_addrlen)) {
				break; // Connected
			} else {
				mSocket = INVALID_SOCKET;
				closesocket(mSocket); // Unable to connect
			}
		}
		freeaddrinfo(address);
		return mSocket != INVALID_SOCKET;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	void Socket::close() {
		if(mSocket == INVALID_SOCKET)
			return; // Nothing to do here
		closesocket(mSocket);
		mSocket = INVALID_SOCKET;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::isOpen() const {
		return (SOCKET_ERROR != mSocket);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::write(const std::string& _s) {
		return write(_s.size(), _s.c_str());
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::write(unsigned _length, const void* _data) {
		return SOCKET_ERROR != send(mSocket, (const char*)_data, _length, 0);
	}

	//------------------------------------------------------------------------------------------------------------------
	int Socket::read(void* _data, unsigned _maxLength) {
		
		int nBytes = recv(mSocket, reinterpret_cast<char*>(_data), _maxLength, 0);
		if(0 == nBytes)
			close();
		else if(nBytes == SOCKET_ERROR) {
			std::cout << "Socket " << mSocket << " failed to read with error "
#ifdef _WIN32
				<< WSAGetLastError() 
#endif // _WIN32
				<< "\n";
			return -1;
		}
		return nBytes;
	}

}	// namespace dmc