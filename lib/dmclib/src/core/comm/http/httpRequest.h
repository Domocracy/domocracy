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
#include "httpMessage.h"

namespace dmc { namespace http {

	class Request : Message {
	public:
		enum METHOD {
			Get,
			Post,
			Put
		};
		// Construction
		Request(METHOD, const std::string& _url, // Status line
				const std::vector<std::string>& _headers, // Headers
				const std::string& _body); // Body
		Request(const std::string& _rawRequest);

		// Accessors
		METHOD				method		() const { return mMethod; }
		const std::string&	url			() const { return mUrl; }

	private:
		int		processMessageLine		(const std::string& _raw) override;
		void	serializeMessageLine	(std::string& dst) const override;

	private:
		METHOD mMethod;
		std::string mUrl;
	};

}}	// namespace dmc::http

#endif // _DMCLIB_CORE_COMM_HTTP_HTTPREQUEST_H_