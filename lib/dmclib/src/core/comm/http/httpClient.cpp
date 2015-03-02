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
		return mSocket.open(_url, _port);
	}

	//------------------------------------------------------------------------------------------------------------------
	Response* Client::makeRequest(const Request& _req) {
		// Ensure connection
		if(!mSocket.isOpen()) {
			auto iter = _req.headers().find("Host");
			if(iter == _req.headers().end())
				return nullptr;
			if(!mSocket.open(iter->second.c_str(), 80))
				return nullptr;
		}

		// Send request
		if(!mSocket.write(_req.serialize()))
			return nullptr;

		// Wait for response from the server
		const unsigned bufferSize = 64*1024;
		char buffer[bufferSize+1];
		int nBytes = mSocket.read(buffer, bufferSize);
		buffer[nBytes] = '\0';
		std::string dst(buffer);
		if(nBytes < 0) {
				std::cout << "Error reading from socket: " << errno << "\n";
			return nullptr;
		}
		// std::cout << "Recv: " << dst;
		Response * resp = new Response(dst); // Keep reading
		while(nBytes > 0 && resp && !resp->isComplete()) {
			nBytes = mSocket.read(buffer, bufferSize);
			buffer[nBytes] = '\0';
			dst = std::string(buffer);
			// std::cout << "Recv: " << dst;
			*resp << dst;
		}

		// House keeping (close socket on non-persistent connections)
		if(resp->requiresClose() || _req.requiresClose()) // Non persistent connection
			mSocket.close();

		// Return server response
		return resp;
	}

	//------------------------------------------------------------------------------------------------------------------

}}	// namespace dmc::http
