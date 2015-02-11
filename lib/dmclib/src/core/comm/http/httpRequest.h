//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-10
//----------------------------------------------------------------------------------------------------------------------
// request over http protocol
#ifndef _DMCLIB_CORE_COMM_HTTP_HTTPREQUEST_H_
#define _DMCLIB_CORE_COMM_HTTP_HTTPREQUEST_H_

#include <string>
#include <unordered_map>
#include <vector>

namespace dmc { namespace http {

	class Request {
	public:

		enum METHOD {
			Get,
			Post,
			Put
		};
		// Construction
		//Request() = default;
		Request(METHOD, const std::string& _url, // Status line
				const std::vector<std::string>& _headers, // Headers
				const std::string& _body); // Body
		Request(const std::string& _rawRequest);

		// Internal check
		bool isOk() const { return mIsOk; }

		// Accessors
		METHOD												method	() const { return mMethod; }
		const std::string&									url		() const { return mUrl; }
		const std::unordered_map<std::string,std::string>&	headers	() const { return mHeaders; }
		std::unordered_map<std::string,std::string>&		headers	()		 { return mHeaders; }
		const std::string&									body	() const { return mBody; }

		bool												addHeader	(const std::string&);

		std::string											serialize	() const;
	private:
		bool			processRequestLine	(const std::string&);
		bool			processHeaders		(const std::string&);

		void			serializeRequestLine(std::string& dst) const;
		void			serializeHeaders	(std::string& dst) const;

	private:
		METHOD mMethod;
		std::string mUrl;
		std::unordered_map<std::string,std::string>	mHeaders;
		std::string mBody;
		bool mIsOk = false;
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPREQUEST_H_