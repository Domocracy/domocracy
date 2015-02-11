////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "user.h"
#include <core/comm/http/httpServer.h>
#include <core/comm/http/response/jsonResponse.h>
#include <string>

using namespace std;

namespace dmc {

	using namespace http;

	//------------------------------------------------------------------------------------------------------------------
	User::User(const Json& _userData, Server* _serviceToListen)
	{
		mName = _userData["name"].asText();
		_serviceToListen->setResponder(string("/dev/") + mName, [this](Server* _s, unsigned _conId, const Request& _request){
			_s->respond(_conId, JsonResponse(Json(R"("ok")")));
		});
	}

}	// namespace dmc