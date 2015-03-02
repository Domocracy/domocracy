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

namespace {

	/*struct Btaddr {
		uint8_t b[6];
	};

	struct SockAddrBth {
		USHORT  addressFamily;
		Btaddr	btAddr;
		GUID    serviceClassId;
		ULONG   port;
	};

	// Translate bluetooth addresses to mac addresses
	Btaddr str2ba(const std::string& _str)
	{
		assert(_str.size() == 17);
		for(unsigned i = 1; i < 5; ++i)
			assert(_str[3*i-1] == ':');
		unsigned ptr = 0;

		Btaddr b;
		for(unsigned i = 0; i < 6; i++, ptr+=3) {
			const std::string segment = _str.substr(ptr, 2);
			b.b[5-i] = (uint8_t)strtol(segment.c_str(), nullptr, 16);
		}
		return b;
	}*/
}

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
	bool Socket::open(const std::string& _url, unsigned _port, Protocol _protocol) {
		if(_protocol == Protocol::RFCOMM) {
			SOCKADDR_BTH sab;
			BTH_ADDR aSddr = 0xCCAF78B9A79C;

			memset (&sab, 0, sizeof(sab));
            sab.addressFamily  = AF_BTH;
            sab.btAddr = aSddr;
            sab.port = 1;

			mSocket = socket(AF_BTH, SOCK_STREAM, BTHPROTO_RFCOMM);
			if(mSocket == INVALID_SOCKET){
				std::cout << "Unable to create bluetooth socket\n";
				return false;
			}
			SocketDesc result = connect( mSocket, reinterpret_cast<sockaddr*>(&sab), sizeof(sab));
			if (result == SOCKET_ERROR) {
				std::cout << "Unable to connect to bluetooth socket\nError: " << WSAGetLastError() << "\n";
			}
			return result != SOCKET_ERROR;
		}
		mMustClose = false;
		if(!getSocketAddress(_url, _port, _protocol))
			return false;

		// ----- Try to connect -----
		for(mAddress = mSystemAddress; nullptr != mAddress; mAddress = mAddress->ai_next) { // Iterate over all possible adresses
			if(!openSocket())
				continue;

			if(needsConnect(_protocol)) {
				// Connect to server.
				SocketDesc result = connect( mSocket, mAddress->ai_addr, (int)mAddress->ai_addrlen);
				if(SOCKET_ERROR != result) {
					break; // Connected
				} else {
					mSocket = INVALID_SOCKET;
					closesocket(mSocket); // Unable to connect
				}
			} else {
				break;
			}
		}
		return mSocket != INVALID_SOCKET;
	}
	
	//------------------------------------------------------------------------------------------------------------------
	void Socket::close() {
		// if(mSystemAddress) {
		// 	freeaddrinfo(mSystemAddress);
		// 	mSystemAddress = nullptr;
		// }
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

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::getSocketAddress(const std::string& _url, unsigned _port, Protocol _protocol) {
		// ------ Get proper address info -------
		struct addrinfo addrHints;
		mAddress = nullptr;
		memset(&addrHints, 0, sizeof(addrHints));
		addrHints.ai_family = AF_UNSPEC;		// Connect to either ip v4 or v6
		switch(_protocol) {
		case Protocol::TCP:
			addrHints.ai_socktype = SOCK_STREAM;	// Connection type TCP IP
			addrHints.ai_protocol = IPPROTO_TCP;
			break;
		case Protocol::UDP:
			addrHints.ai_socktype = SOCK_DGRAM;
			addrHints.ai_protocol = IPPROTO_UDP;
			break;
		}
		// --- Query the OS for an address fitting the description
		std::stringstream portStream;
		portStream << _port;
		int res = getaddrinfo(_url.c_str(), portStream.str().c_str(), &addrHints, &mSystemAddress);
		if(0 != res)
		{
			std::cout << "Error: unable to get addr info for socket\n\t-url: "
				<< _url << "\n\t-port: " << portStream.str() << "\n";
			return false;
		}

		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::openSocket() {
		mSocket = socket(mAddress->ai_family,
						mAddress->ai_socktype,
						mAddress->ai_protocol);
		if (mSocket == INVALID_SOCKET) {
			std::cout << "socket failed to open\n";
			return false;
		}
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Socket::needsConnect(Protocol _protocol) {
		return _protocol == Protocol::TCP || _protocol == Protocol::RFCOMM;
	}

}	// namespace dmc