//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-23
//----------------------------------------------------------------------------------------------------------------------
// http response containing only json information
#ifndef _DMCLIB_CORE_COMM_HTTP_RESPONSE_JSONRESPONSE_H_
#define _DMCLIB_CORE_COMM_HTTP_RESPONSE_JSONRESPONSE_H_

#include "../httpResponse.h"
#include <core/comm/json/json.h>

namespace dmc { namespace http {

	class JsonResponse : public Response {
	public:
		JsonResponse(const Json& _rawResponse);
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_RESPONSE_JSONRESPONSE_H_