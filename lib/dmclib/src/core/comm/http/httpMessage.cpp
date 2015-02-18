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
	const string Message::cContentLengthLabel = "Content-Length";

	//----------------------------------------------------------------------------------------------------------------------
	Message::Message(const string& _raw) {
		operator<<(_raw);
	}

	//----------------------------------------------------------------------------------------------------------------------
	int Message::operator<<(const string& _raw) {
		// use mBody as a temporal buffer
		mBody.append(_raw);
		int totalConsumed = 0;
		while(!mBody.empty()) {
			switch(mState) {
			case ParseState::MessageLine: {
				int consumed = parseMessageLine();
				if(consumed > 0)
					totalConsumed += consumed;
				else
					return consumed; // Error( ==0 ) or need more to parse ( < 0 )
				break;
			}
			case ParseState::Headers: {
				int consumed = parseMessageLine();
				if(consumed > 0)
					totalConsumed += consumed;
				else
					return consumed; // Error( ==0 ) or need more to parse ( < 0 )
				break;
			}
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
		mHeaders[cContentLengthLabel]=ss.str();
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
	bool Message::needBody() const {
		auto iter = mHeaders.find(cContentLengthLabel);
		if(iter != mHeaders.end()) {
			return atoi(iter->second.c_str()) != 0;
		}
		return false;
	}

	//----------------------------------------------------------------------------------------------------------------------
	bool Message::processHeaders(const string& _headers) {
		string left = _headers;
		while(!left.empty())
		{
			unsigned split = left.find("\r\n");
			string header;
			if(split == string::npos) {
				header = left;
				left.clear();
			} else {
				 header = left.substr(0,split);
				 left = left.substr(split+2);
			}
			if(!addHeader(header))
				return false;
		}
		return true;
	}

	//----------------------------------------------------------------------------------------------------------------------
	int Message::parseMessageLine() {
		unsigned cursor = mBody.find("\r\n");
		if(cursor != string::npos) // Line end found
		{
			int consumed = processMessageLine(mBody.substr(0,cursor));
			if(consumed > 0) // success
			{
				mBody = mBody.substr(consumed);
				mState = ParseState::Headers;
			}
			else { // Parsing error
				mState = ParseState::Error;
				return -1;
			}
		}
		else // No line end found, need more text
			return 0;
	}

	//----------------------------------------------------------------------------------------------------------------------
	int Message::parseHeaders() {
		bool skipHeaders = mBody.find("\r\n") == 0; // if we find a blank line right at the begining, then there are no headers
		if(skipHeaders) {
			ParseState::Body; // Waiting for the body
			return 2;
		}
		unsigned consumed = mBody.find("\r\n\r\n");
		if(consumed != string::npos)
		{
			bool success = processHeaders(mBody.substr(0,consumed));
			if(!success) { // Parsing error
				mState = ParseState::Error;
				return -1;
			}
			mBody = mBody.substr(consumed);
			// Should we expect a body?
			mState = needBody()?ParseState::Body:ParseState::Complete;
			return consumed;
		}
		return 0; // End of headers not yet found
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