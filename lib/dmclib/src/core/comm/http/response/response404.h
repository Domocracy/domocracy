////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCLIB_CORE_COMM_HTTP_RESPONSE_RESPONSE404_H_
#define _DMCLIB_CORE_COMM_HTTP_RESPONSE_RESPONSE404_H_

#include "../httpResponse.h"
#include <string>

namespace dmc { namespace http {

	class Response404 : public Response {
	public:
		Response404() : Response(404, "Not Found")
		{}

		Response404(const std::string& _customBody): Response(404, "Not Found") {
			setBody(_customBody);
		}
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_RESPONSE_RESPONSE404_H_