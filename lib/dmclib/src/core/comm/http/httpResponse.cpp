//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-15
//----------------------------------------------------------------------------------------------------------------------
// Http protocol's response to a request
#include <cassert>
#include "httpResponse.h"
#include <sstream>
#include <core/platfrom/file/file.h>

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
	Response Response::response200(const std::string& _customMessage) {
		Response r(200, shortDesc(200));
		r.setBody(_customMessage);
		return r;
	}

	//------------------------------------------------------------------------------------------------------------------
	Response Response::response404(const std::string& _customMessage) {
		Response r(404, shortDesc(404));
		r.setBody(_customMessage);
		return r;
	}

	//------------------------------------------------------------------------------------------------------------------
	Response Response::jsonResponse(const Json& _payload, unsigned _code) {
		Response r(_code, shortDesc(_code));
		r.setBody(_payload.serialize());
		r.headers()["Content-type"] = "application/json";
		return r;
	}

	//------------------------------------------------------------------------------------------------------------------
	Response Response::htmlResponse(const std::string& _fileName, unsigned _code) {
		// Open file
		File* pageFile = File::openExisting(_fileName);
		if(pageFile) {
			Response r(_code, shortDesc(_code));
			pageFile->readAll();
			r.setBody(pageFile->bufferAsText());
			r.headers()["Content-type"] = "text/html";
			return r;
		}
		else return response404();
	}

	//------------------------------------------------------------------------------------------------------------------
	std::string Response::shortDesc(unsigned _code) {
		switch (_code) {
		case 200:
			return "Ok";
		case 404:
			return "Not Found";
		default:
			assert(false);
			return "";
		}
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