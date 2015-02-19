////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "kodi.h"
#include <core/comm/json/rpc/jsonRpc.h>
#include <core/comm/http/httpClient.h>
#include <core/comm/http/request/jsonRequest.h>
#include <iostream>

namespace dmc { namespace kodi {

	//------------------------------------------------------------------------------------------------------------------
	Kodi::Kodi(const Json& _data) 
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		mPort = (unsigned)_data["port"].asInt();
		mIp = _data["ip"].asText();

		// Try to ping Kodi
		http::Client client;
		if(!client.connect(mIp, mPort)) {
			std::cout << "Can't connect to Kodi\n";
		}
		else { // Connected
			std::cout << "Connected to Kodi\n";

			Json notifParams(R"({"title":"DMC", "message": "Domocratizate!"}")");
			JsonRpcRequest request("GUI.ShowNotification", notifParams, 352);

			http::Response* r = client.makeRequest(http::JsonRequest(http::Request::METHOD::Post, "/jsonrpc", request));
			std::cout << r->serialize() << "\n";
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::runCommand(const Json&) {
		return true;
	}

}}	// namespace dmc::kodi