//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-15
//----------------------------------------------------------------------------------------------------------------------
// request over http protocol
#include "httpClient.h"
#include <iostream>

#include <core/comm/socket/socket.h>
#include <core/comm/http/httpRequest.h>
#include <core/comm/http/httpResponse.h>

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	bool Client::connect(const std::string& _url, unsigned _port) {
		mUrl = _url;
		return mSocket.connectTo(_url, _port);
	}

	//------------------------------------------------------------------------------------------------------------------
	Response* Client::makeRequest(const Request& _req) {
		// Ensure connection
		if(!mSocket.isOpen()) {
			auto iter = _req.headers().find("Host");
			if(iter == _req.headers().end())
				return nullptr;
			if(!mSocket.connectTo(iter->second.c_str(), 80))
				return nullptr;
		}

		// Send request
		if(!mSocket.write(_req.serialize()))
			return nullptr;

		// Wait for response from the server
		const unsigned bufferSize = 64*1024;
		char buffer[bufferSize+1];
		int nBytes = 0;
		std::string dst;
		nBytes = mSocket.read(buffer, bufferSize);
		if(nBytes <= 0) {
			if(nBytes < 0)
				std::cout << errno << "\n";
			return nullptr;
		}
		buffer[nBytes] = '\0';
		dst += buffer;
		
		Response * resp = new Response(dst);

		// House keeping (close socket on non-persistent connections)
		auto servConnHeader = resp->headers().find("Connection");
		bool servClose = (servConnHeader != resp->headers().end()) && (servConnHeader->second == "close");
		auto clientConnHeader = _req.headers().find("Connection");
		bool clientClose = (clientConnHeader != _req.headers().end())
			&& (clientConnHeader->second == "close");
		if(servClose || clientClose) // Non persistent connection
			mSocket.close();

		// Return server response
		return resp;
	}

	//------------------------------------------------------------------------------------------------------------------

}}	// namespace dmc::http
