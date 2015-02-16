//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Feb-12
//----------------------------------------------------------------------------------------------------------------------
// Json based http request
#ifndef _DMCLIB_CORE_COMM_HTTP_REQUEST_JSONREQUEST_H_
#define _DMCLIB_CORE_COMM_HTTP_REQUEST_JSONREQUEST_H_

#include "../httpRequest.h"
#include <core/comm/json/json.h>
#include <string>
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	class JsonRequest : public Request {
	public:
		JsonRequest(METHOD _method, const std::string& _url, const Json& _data)
			:Request(_method, _url, std::vector<std::string>(), "")
		{
			headers()["Content-Type"] = "application/json; charset=UTF-8";
			std::string newBody;
			_data >> newBody;
			setBody(newBody);
		}
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_REQUEST_JSONREQUEST_H_