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
#include "httpMessage.h"

namespace dmc { namespace http {

	class Response : public Message {
	public:
		Response(const std::string& _rawResponse);
		Response(unsigned _statusCode, const std::string& _desc);
		
		unsigned			statusCode	() const { return mStatusCode; }
		const std::string&	statusDesc	() const { return mStatusDesc; }

	private:
		int		processMessageLine	(const std::string& _raw) override;
		void	serializeMessageLine(std::string& dst) const override;

		unsigned mStatusCode;
		std::string mStatusDesc;
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPRESPONSE_H_