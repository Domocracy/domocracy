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
#include <core/comm/http/response/response404.h>
#include <core/comm/http/response/response200.h>
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
		mPrefixSize = (string("/dev/") + mName + "/").size();
		_serviceToListen->setResponder(string("/dev/") + mName, [this](Server* _s, unsigned _conId, const Request& _request){
			string url = _request.url();
			if(url.size() <= mPrefixSize) // Request state
			{
				Response200 r;
				r.setBody("666 TODO: Show list of devices and rooms available to the user\n");
				_s->respond(_conId, r);
				return;
			}
			string command = url.substr(mPrefixSize);
			Actuator* device = dynamic_cast<Actuator*>(mDevices->get(42));
			if(!device)
				_s->respond(_conId, Response404());
			if(command == "on")
			{

				device->runCommand(Json(R"({"on":true})"));
				_s->respond(_conId, Response200());
			} else if (command == "off")
			{
				device->runCommand(Json(R"({"on":false})"));
				_s->respond(_conId, Response200());
			} else
				_s->respond(_conId, Response404());
		});
	}

}	// namespace dmc