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
		this->operator<<(_rawResponse);
	}

	//------------------------------------------------------------------------------------------------------------------
	Response::Response(unsigned _statusCode, const std::string& _desc)
		: mStatusCode(_statusCode)
		, mStatusDesc(_desc)
	{
		setReady();
	}

	//------------------------------------------------------------------------------------------------------------------
	int Response::processMessageLine(const std::string& _status) {
		const string separators = " \t\n\r";
		unsigned versionEnd = _status.find_first_of(separators);
		unsigned codeStart = _status.find_first_not_of(separators, versionEnd+1);
		mStatusCode = (unsigned)atoi(_status.substr(codeStart).c_str());
		unsigned codeEnd = _status.find_first_of(separators, codeStart+1);
		mStatusDesc = _status.substr(codeEnd+1);
		return _status.size();
	}

	//------------------------------------------------------------------------------------------------------------------
	void Response::serializeMessageLine(std::string& _serial) const {
		std::stringstream serial;
		// Status line
		serial << "HTTP/1.1 " << mStatusCode << " " << mStatusDesc << "\r\n";
		_serial.append(serial.str());
	}

}}	// namespace dmc::http