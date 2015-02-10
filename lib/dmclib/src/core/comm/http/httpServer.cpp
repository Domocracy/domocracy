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

using namespace std;

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	Server::Server(unsigned _port) {
		mSocket = new SocketServer(_port);
		assert(mSocket);
		std::cout << "Http server listening on port " << _port << "\n";

		// Set default responder to http requests
		mSocket->setConnectionHandler([this](Socket* _connection) {
			onNewConnection(_connection);
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

	//------------------------------------------------------------------------------------------------------------------
	void Server::onNewConnection(Socket* _connection) {
		// Generate a connection identifier
		unsigned conId = reinterpret_cast<unsigned>(_connection);
		mConnections[conId] = _connection;
		// Retrieve request
		std::string message;
		const unsigned buffSize = 2*1024;
		char buffer[buffSize];
		unsigned len = _connection->read(buffer, buffSize);
		if(len > 0) {
			message.append(buffer, len);
			Request petition = Request(message);

			// Locate proper responder
			if(!dispatchPetition(this, petition.url(), conId, petition))
				sendError404(_connection);
		}
		// Clean up
		_connection->close();
		mConnections.erase(mConnections.find(conId));
		delete _connection;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Server::sendError404(Socket* _connection) const {
		std::string notFound = "HTTP/1.1 404 Not Found\r\n\r\nThe Url you are requesting is not available\r\n";
		_connection->write(notFound.size(), notFound.c_str());
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Server::dispatchPetition(Server* _server, const std::string& _url, unsigned _conId, const Request& _petition) {
		if(_url.empty())
			return false;
		string key = _url[0]=='/'?_url:(string("/")+_url); // Always start with a slash
		while(!key.empty()) {
			// Try key
			auto iter = mHandlers.find(key);
			if(iter != mHandlers.end()) {
				iter->second(_server, _conId, _petition); // Invoke handler
				return true; 
			}
			// Not found, keep decomposing the url
			unsigned lastSlash = key.find_last_of('/');
			key = key.substr(0, lastSlash);
		}
		return false;
	}
	
}}	// namespace dmc::http