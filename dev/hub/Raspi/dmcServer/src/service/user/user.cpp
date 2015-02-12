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
#include <provider/deviceMgr.h>
#include <home/device/actuator.h>

using namespace std;

namespace dmc {

	using namespace http;

	//------------------------------------------------------------------------------------------------------------------
	User::User(const Json& _userData, Server* _serviceToListen, DeviceMgr* _devMgr)
		:mDevices(_devMgr)
	{
		mName = _userData["name"].asText();
		_serviceToListen->setResponder(string("/dev/") + mName, [this](Server* _s, unsigned _conId, const Request& _request){
			// Try any device
			dynamic_cast<Actuator*>(mDevices->get(42))->runCommand(Json());
			_s->respond(_conId, JsonResponse(Json(R"("ok")")));
		});
	}

}	// namespace dmc