//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// Simple http server
#ifndef _DMCLIB_CORE_COMM_HTTP_HTTPSERVER_H_
#define _DMCLIB_CORE_COMM_HTTP_HTTPSERVER_H_

#include <functional>
#include <string>

#include "httpResponse.h"
#include "httpRequest.h"

#include <string>
#include <unordered_map>

namespace dmc {

	class Socket;
	class SocketServer;

	namespace http {
		class Server {
		public:
			// Construction
			Server(unsigned _port);
			~Server();

			// 666 TODO: Start or stop listening
			// void listen(bool);
			void respond(unsigned _conId, const Response&);

			// Configure responses
			typedef std::function<void (Server*, unsigned _conId, const Request&)> UrlHandler;
			void setResponder(const std::string& _url, UrlHandler _responder);

		private:
			void onNewConnection(Socket* _socket);
			void sendError404(Socket* _connection) const;
			bool dispatchPetition(Server*, const std::string& _url, unsigned _conId, const Request& _petition);

		private:
			SocketServer*	mSocket;

			std::unordered_map<std::string, UrlHandler>	mHandlers;
			std::unordered_map<unsigned, Socket*>		mConnections;
		};
	}	// namespace http
}	// namespace dmc

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPSERVER_H_