//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Feb-17
//----------------------------------------------------------------------------------------------------------------------
// Base class underlying http requests and responses
#include <cassert>
#include "httpMessage.h"
#include <sstream>

using namespace std;

namespace dmc { namespace http {

	//----------------------------------------------------------------------------------------------------------------------
	Message::Message(const string& _raw) {
		operator<<(_raw);
	}

	//----------------------------------------------------------------------------------------------------------------------
	int Message::operator<<(const string& _raw) {
		// use mBody as a temporal buffer
		mBody.append(_raw);
		while(!mBody.empty()) {
			switch(mState) {
			case ParseState::MessageLine:
				if(mBody.find("\r\n") != string::npos) // Line end found
				{
					unsigned consumed = processMessageLine(mBody);
					if(consumed < 0) // error
					{
						mState = ParseState::Error;
						return -1;
					}
					mBody = mBody.substr(consumed);
					mState = ParseState::Headers;
				}
				break;
			case ParseState::Headers:
				if(mBody.find("\r\n\r\n") != string::npos)
				{
					unsigned consumed = processHeaders(mBody);
					if(consumed < 0) { // Parsing error
						mState = ParseState::Error;
						return -1;
					}
					mBody = mBody.substr(consumed);
					// Should we expect a body?
					mState = needBody()?ParseState::Body:ParseState::Complete;
					return isComplete()?consumed:0;
				}
				break;
			case ParseState::Body:
				if(needBody()) // When body is necessary, body length must be known
				{
					if(_raw.size() < mMissingBodyLength)
						return 0; // Keep processing
					int leftResources = _raw.size() - mMissingBodyLength;
					int bodySize = mBody.size() - leftResources;
					mBody = mBody.substr(0, bodySize);
					int consumed = mMissingBodyLength;
					mMissingBodyLength = 0;
					mState = ParseState::Complete;
					return consumed;
				} else { // Otherwise, body will be as long as available
					return 0;
				}
				break;
			default:
				return -1; // Nothing left to parse
			}
		}
		return -1;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Message::isComplete() const {
		return mState == ParseState::Complete;
	}

	//----------------------------------------------------------------------------------------------------------------------
	void Message::setBody(const std::string& _body) {
		mBody = _body;
		stringstream ss;
		ss << body().size();
		mHeaders["Content-Length"]=ss.str();
	}

	//----------------------------------------------------------------------------------------------------------------------
	string Message::serialize() const {
		string serial;
		serializeMessageLine(serial);
		serializeHeaders	(serial);
		if(mBody.size())
			serial.append(mBody + "\r\n");
		return serial;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Message::addHeader(const string& _headerLine) {
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