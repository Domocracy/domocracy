//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// Simple http server
#include <core/comm/socket/socketServer.h>
#include <cassert>
#include "httpResponse.h"
#include "httpServer.h"
#include <iostream>
#include <string>

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	Server::Server(unsigned _port) {
		mSocket = new SocketServer(_port);
		assert(mSocket);
		std::cout << "Http server listening on port " << _port << "\n";

		// Set default responder to http requests
		mSocket->setConnectionHandler([this](Socket* _connection) {
			// Retrieve request
			std::string message;
			const unsigned buffSize = 2*1024;
			char buffer[buffSize];
			unsigned len = _connection->read(buffer, buffSize);
			if(len > 0) {
				message.append(buffer, len);

				Request petition = Request(message);

				// Locate proper responder
				auto iter = mHandlers.find(petition.url());
				if(iter != mHandlers.end()) {
					unsigned conId = reinterpret_cast<unsigned>(_connection);
					mConnections[conId] = _connection;
					iter->second(this, conId, petition);
				} else {
					std::string notFound = "HTTP/1.1 404 Not Found\r\n";
					_connection->write(notFound.size(), notFound.c_str());
				}
			}
			_connection->close();
			delete _connection;
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	Server::~Server() {
		delete mSocket;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Server::respond(unsigned _conId, const Response& _response) {
		mConnections[_conId]->write(_response.serialize());
	}

	//------------------------------------------------------------------------------------------------------------------
	void Server::setResponder(const std::string& _url, UrlHandler _handler) {
		mHandlers[_url] = _handler;
	}
	
}}	// namespace dmc::http