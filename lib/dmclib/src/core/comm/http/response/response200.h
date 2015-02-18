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

	class Response200 : public Response {
	public:
		Response200() : Response(200, "Ok")
		{
		}

		Response200(const std::string& _customBody) : Response(200, "Ok")
		{
			setBody(_customBody);
		}
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_RESPONSE_H_