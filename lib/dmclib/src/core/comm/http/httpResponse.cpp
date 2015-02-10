//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-15
//----------------------------------------------------------------------------------------------------------------------
// Http protocol's response to a request
#include "httpResponse.h"
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//------------------------------------------------------------------------------------------------------------------
	Response::Response(const std::string& _rawResponse) {
		// Split response in status, headers and body
		unsigned statusLen = _rawResponse.find("\r\n");
		string statusLine = _rawResponse.substr(0,statusLen);
		unsigned headerEnd = _rawResponse.find("\r\n\r\n", statusLen+2);
		unsigned headerLen = headerEnd - statusLen - 2;
		string headers = _rawResponse.substr(statusLen+2, headerLen);
		mBody = _rawResponse.substr(headerEnd+4);

		// Process each part
		processStatus(statusLine);
		processHeaders(headers);
	}

	//------------------------------------------------------------------------------------------------------------------
	Response::Response(unsigned _statusCode, const std::string& _desc)
		: mStatusCode(_statusCode)
		, mStatusDesc(_desc)
	{
		// Intentionally blank
	}

	//------------------------------------------------------------------------------------------------------------------
	std::string Response::serialize() const {
		std::stringstream serial;
		// Status line
		serial << "HTTP/1.1 " << mStatusCode << " " << mStatusDesc << "\r\n";
		// Headers
		for(auto h : mHeaders)
			serial << h.first << ": " << h.second << "\r\n";
		// Blank line
		serial << "\r\n";
		if(mBody.size())
			serial << mBody << "\r\n";

		return serial.str();
	}

	//------------------------------------------------------------------------------------------------------------------
	void Response::processStatus(const std::string& _status) {
		const string separators = " \t\n\r";
		unsigned versionEnd = _status.find_first_of(separators);
		unsigned codeStart = _status.find_first_not_of(separators, versionEnd+1);
		mStatusCode = (unsigned)atoi(_status.substr(codeStart).c_str());
		unsigned codeEnd = _status.find_first_of(separators, codeStart+1);
		mStatusDesc = _status.substr(codeEnd);
	}

	//------------------------------------------------------------------------------------------------------------------
	void Response::processHeaders(const std::string& _headers) {
		unsigned lastSplit = 0;
		unsigned lineSplit = _headers.find("\r\n");
		while(lineSplit != string::npos) {
			string line = _headers.substr(lastSplit, lineSplit-lastSplit);
			unsigned split = line.find(": ");
			string label = line.substr(0,split);
			string value = line.substr(split+2);
			mHeaders.insert(make_pair(label,value));

			lastSplit = lineSplit+2;
			lineSplit = _headers.find("\r\n", lastSplit);
		}
	}

}}	// namespace dmc::http