//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// request over http protocol
#include <cassert>
#include "httpRequest.h"
#include <iostream>
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//----------------------------------------------------------------------------------------------------------------------
	Request::Request(METHOD _method, const string& _url, // Status line
				const vector<string>& _headers, // Headers
				const string& _body) // Body
		:mMethod(_method)
		,mUrl(_url)
	{
		for(auto h : _headers)
			addHeader(h);
		setBody(_body);
		setReady();
	}

	//----------------------------------------------------------------------------------------------------------------------
	Request::Request(const string& _raw) {
		// Break the string into parts
		this->operator<<(_raw);
		// unsigned requestEnd = _raw.find("\r\n");
		// unsigned headersEnd = _raw.find("\r\n\r\n", requestEnd+2);
		// string request = _raw.substr(0,requestEnd);
		// string headers = _raw.substr(requestEnd+2, headersEnd-requestEnd-2);
		// mBody = _raw.substr(headersEnd+4);
		// // Process request line
		// bool rOk = processRequestLine(request);
		// bool hOk = processHeaders(headers);
		// mIsOk = rOk && hOk;
	}

	//----------------------------------------------------------------------------------------------------------------------
	int Request::processMessageLine(const std::string& _requestLine) {
		unsigned methodLen = _requestLine.find(" ");
		string method = _requestLine.substr(0,methodLen);
		if(method == "GET")
			mMethod = Get;
		else if (method == "POST")
			mMethod = Post;
		else if(method == "PUT")
			mMethod = Put;
		else {
			cout << "Error: Unsupported method parsing http request\n"
				<< method << "\n";
			return -1;
		}

		unsigned urlEnd = _requestLine.find(" ", methodLen+1);
		if(urlEnd == string::npos)
			return -1;
		mUrl = _requestLine.substr(methodLen+1, urlEnd - methodLen -1);
		if(mUrl.empty())
			return -1;
		return _requestLine.size();
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Request::processRequestLine(const string& _requestLine) {
		
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