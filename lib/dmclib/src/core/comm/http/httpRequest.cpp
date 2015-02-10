//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// request over http protocol
#include <cassert>
#include "httpRequest.h"
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//----------------------------------------------------------------------------------------------------------------------
	Request::Request(METHOD _method, const string& _url, // Status line
				const vector<string>& _headers, // Headers
				const string& _body) // Body
		:mMethod(_method)
		,mUrl(_url)
		,mBody(_body)
	{
		for(auto h : _headers)
			addHeader(h);
	}

	//----------------------------------------------------------------------------------------------------------------------
	Request::Request(const string& _raw) {
		// Break the string into parts
		unsigned requestEnd = _raw.find("\r\n");
		unsigned headersEnd = _raw.find("\r\n\r\n", requestEnd+2);
		string request = _raw.substr(0,requestEnd);
		string headers = _raw.substr(requestEnd+2, headersEnd-requestEnd-2);
		mBody = _raw.substr(headersEnd+4);
		// Process request line
		bool rOk = processRequestLine(request);
		bool hOk = processHeaders(headers);
		mIsOk = rOk && hOk;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Request::addHeader(const string& _headerLine) {
		unsigned colonPos = _headerLine.find(": ");
		if(colonPos == string::npos)
			return false;
		string label = _headerLine.substr(0,colonPos);
		string content = _headerLine.substr(colonPos+2);
		mHeaders.insert(make_pair(
			label,
			content
			));
		return true;
	}

	//----------------------------------------------------------------------------------------------------------------------
	string Request::serialize() const {
		string serial;
		serializeRequestLine(serial);
		serializeHeaders	(serial);
		if(mBody.size())
			serial.append(mBody + "\r\n");
		return serial;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Request::processRequestLine(const string& _requestLine) {
		unsigned methodLen = _requestLine.find(" ");
		string method = _requestLine.substr(0,methodLen);
		if(method == "GET")
			mMethod = Get;
		else if (method == "POST")
			mMethod = Post;
		else if(method == "PUT")
			mMethod = Put;
		else
			return false;

		unsigned urlEnd = _requestLine.find(" ", methodLen+1);
		if(urlEnd == string::npos)
			return false;
		mUrl = _requestLine.substr(methodLen+1, urlEnd - methodLen -1);
		if(mUrl.empty())
			return false;
		return true;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Request::processHeaders(const string& _headers) {
		string left = _headers;
		unsigned split = 0;
		while((split = left.find("\r\n")) != string::npos) {
			string header = left.substr(0,split);
			left = left.substr(split+2);
		}
		return true;
	}

	//----------------------------------------------------------------------------------------------------------------------
	void Request::serializeRequestLine(string& _dst) const {
		stringstream statusLine;
		switch (mMethod)
		{
		case Get:
			statusLine << "GET";
			break;
		case Post:
			statusLine << "POST";
			break;
		case Put:
			statusLine << "PUT";
			break;
		default:
			assert(false);
			break;
		}
		statusLine << " " << mUrl << " HTTP/1.1\r\n";
		_dst.append(statusLine.str());
	}

	//----------------------------------------------------------------------------------------------------------------------
	void Request::serializeHeaders(string& _dst) const {
		for(auto i : mHeaders) {
			_dst.append(i.first + ": " + i.second + "\r\n");
		}
		_dst.append("\r\n");
	}

}}	// namespace dmc::http