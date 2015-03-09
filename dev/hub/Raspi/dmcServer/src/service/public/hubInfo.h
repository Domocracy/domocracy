////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_SERVICE_PUBLIC_HUBINFO_H_
#define _DMCSERVER_SERVICE_PUBLIC_HUBINFO_H_

#include <core/comm/http/httpServer.h>
#include <core/comm/http/httpResponse.h>

namespace dmc {

	class HubInfo {
	public:
		HubInfo(http::Server* _server) {
			_server->setResponder("/public/hubInfo", http::Response::jsonResponse(Json(R"({"name" : "dmcHub"})")));
		}
		// Services
	};
}	// namespace dmc

#endif // _DMCSERVER_SERVICE_PUBLIC_HUBINFO_H_