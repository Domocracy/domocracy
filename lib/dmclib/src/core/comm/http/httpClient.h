//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-15
//----------------------------------------------------------------------------------------------------------------------
// request over http protocol
#ifndef _DMCLIB_CORE_COMM_HTTP_HTTPCLIENT_H_
#define _DMCLIB_CORE_COMM_HTTP_HTTPCLIENT_H_

#include "core/comm/socket/socket.h"
#include <string>

#include "httpResponse.h"

namespace dmc { 
	class Socket;
	
namespace http {

	class Request;
	class Response;

	class Client {
	public:
		bool connect(const std::string& _url, unsigned _port = 80);

		Response* makeRequest(const Request&);

	private:
		std::string	mUrl;
		Socket		mSocket;
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPCLIENT_H_