////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "hueLight.h"
#include <iostream>
#include <string>
#include <core/comm/http/httpClient.h>

using namespace std;

namespace dmc { namespace hue {

	// Statid data definition
	Bridge* Light::sBridge = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	Light::Light(unsigned _id, const std::string& _name, const std::string& _hueId)
		:Actuator(_id,_name)
		, mHueId(_hueId)
	{
		if(!sBridge)
			sBridge = Bridge::load();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Light::runCommand(const Json& _cmd) {
		if(!sBridge)
			return false;
		std::cout << "Hue light received a command\n";
		string commandUrl = string("/lights/") + mHueId + "/state";
		return sBridge->putData(commandUrl, _cmd);
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Light::read(const Json& _request) const {
		return Json(); // TODO
	}

}}	// namespace dmc::hue