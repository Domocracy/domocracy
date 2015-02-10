//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Feb-07
//----------------------------------------------------------------------------------------------------------------------
// http response containing an html page
#ifndef _DMCLIB_CORE_COMM_HTTP_RESPONSE_HTMLRESPONSE_H_
#define _DMCLIB_CORE_COMM_HTTP_RESPONSE_HTMLRESPONSE_H_

#include "../httpResponse.h"

namespace dmc { namespace http {

	class HtmlResponse : public Response {
	public:
		HtmlResponse(const std::string& _pageFile);
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_RESPONSE_HTMLRESPONSE_H_