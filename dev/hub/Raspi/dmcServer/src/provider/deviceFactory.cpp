////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "deviceFactory.h"
#include <core/comm/json/json.h>
#include <device/hue/hueLight.h>
#include <service/user/device/scene.h>
#include <iostream>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DeviceFactory::DeviceFactory() {
		// Hue lights
		mFactories.insert(std::make_pair("HueLight", [](const Json& _data) -> Device* {
			return new hue::Light(_data["id"].asInt(), _data["name"].asText(), _data["data"]["id"].asText());
		}));
		// Scenes
		mFactories.insert(std::make_pair("Scene", [](const Json& _data) -> Device* {
			return new Scene(_data);
		}));
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceFactory::create(const std::string& _devType, const Json& _data) {
		auto factory = mFactories.find(_devType);
		if(factory == mFactories.end()) {
			std::cout << "Unknown device type " << _devType << ". Ignoring device.\n";
			return nullptr;
		}
		else
			return factory->second(_data);
	}

}	// namespace dmc