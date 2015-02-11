//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Feb-07
//----------------------------------------------------------------------------------------------------------------------
// http response containing an html page
#include "htmlResponse.h"
#include <fstream>
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	HtmlResponse::HtmlResponse(const std::string& _pageFile) 
		: Response(200, "OK")
	{
		// Load page from file
		ifstream htmlFile(_pageFile);
		stringstream readBuff;
		readBuff << htmlFile.rdbuf();
		setBody(readBuff.str());
		// Config response headers
		headers()["Content-type"] = "text/html; charset=UTF-8";//"application/json";
		stringstream size;
		size << body().size();
		headers()["Content-Length"] = size.str();
	}

}}	// namespace dmc::http