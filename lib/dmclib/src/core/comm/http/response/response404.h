////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCLIB_CORE_COMM_HTTP_RESPONSE_H_
#define _DMCLIB_CORE_COMM_HTTP_RESPONSE_H_

#include "../httpResponse.h"
#include <string>

namespace dmc { namespace http {

	class Response404 : public Response {
	public:
		Response404() : Response(404, "Not Found")
		{}
		// 666 TODO: Implement a version of this that loads a webpage from a local html file
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_RESPONSE_H_