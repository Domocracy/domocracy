//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// Server to listen on a port and dispatch incomming connections as new sockets
#ifndef _DMCLIB_CORE_COMM_SOCKET_SOCKETSERVER_H_
#define _DMCLIB_CORE_COMM_SOCKET_SOCKETSERVER_H_

#include "socket.h"
#include <functional>
#include <thread>

namespace dmc {

	class SocketServer {
	public:
		SocketServer(unsigned _port);
		~SocketServer();

		typedef std::function<void(Socket*)>	Handler;
		void setConnectionHandler(Handler _handler);

	private:
		void		startListening(addrinfo*);
		void		dispatchConnection(Socket::SocketDesc);
		void		close();

		addrinfo*	buildAddresInfo			(unsigned _port);
		Socket::SocketDesc		mListener;
		bool		mIsListening = false;
		bool		mMustClose = false;
		std::thread	mListenThread;
		Handler	mConHandler;
	};
}

#endif // _DMCLIB_CORE_COMM_SOCKET_SOCKETSERVER_H_