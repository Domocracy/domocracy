//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015-Jan-15
//----------------------------------------------------------------------------------------------------------------------
// Http protocol's response to a request
#ifndef _DMCLIB_CORE_COMM_HTTP_HTTPRESPONSE_H_
#define _DMCLIB_CORE_COMM_HTTP_HTTPRESPONSE_H_

#include <string>
#include <unordered_map>

namespace dmc { namespace http {

	class Response {
	public:
		Response(const std::string& _rawResponse);
		Response(unsigned _statusCode, const std::string& _desc);
		
		const std::unordered_map<std::string,std::string>&	headers	() const { return mHeaders; }
			  std::unordered_map<std::string,std::string>&	headers	()		 { return mHeaders;	}
		const std::string&									body	() const { return mBody; }

		void												setBody	(const std::string& _b);

		std::string											serialize() const;
		
	private:
		void processStatus(const std::string&);
		void processHeaders(const std::string&);

		unsigned mStatusCode;
		std::string mStatusDesc;
		std::unordered_map<std::string,std::string>	mHeaders;
		std::string mBody;
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPRESPONSE_H_