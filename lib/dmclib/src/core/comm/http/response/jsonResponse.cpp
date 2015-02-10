//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-23
//----------------------------------------------------------------------------------------------------------------------
// http response containing only json information
#include "jsonResponse.h"
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	JsonResponse::JsonResponse(const Json& _rawResponse) 
		: Response(200, "OK")
	{
		string body;
		_rawResponse >> body;
		setBody(body);
		headers()["Content-type"] = "application/json";
		stringstream size;
		size << body.size();
		headers()["Content-Length"] = size.str();
	}

}}	// namespace dmc::http