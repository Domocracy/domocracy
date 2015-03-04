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
#include <service/user/device/kodi/kodi.h>
#include <service/user/device/scene.h>
#include <iostream>

namespace dmc {

	namespace {
		// A wrapper for simplicity
		template<class JDevice_>
		struct JsonConstrucible {
			static std::pair<std::string, std::function<Device*(unsigned, const Json&)>> factory(const std::string& _label) {
				return std::make_pair(_label, [](unsigned _id, const Json& _data) -> Device* {
					return new JDevice_(_id, _data);
				});
			}
		};
	}

	//------------------------------------------------------------------------------------------------------------------
	DeviceFactory::DeviceFactory() {
		// Hue lights
		mFactories.insert(std::make_pair("HueLight", [](unsigned _id, const Json& _data) -> Device* {
			return new hue::Light(_id, _data["name"].asText(), _data["data"]["id"].asText());
		}));
		// Json based devices
		mFactories.insert(JsonConstrucible<Scene>::factory("Scene"));
		mFactories.insert(JsonConstrucible<kodi::Kodi>::factory("Kodi"));
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceFactory::create(unsigned _id, const std::string& _devType, const Json& _data) {
		auto factory = mFactories.find(_devType);
		if(factory == mFactories.end()) {
			std::cout << "Unknown device type " << _devType << ". Ignoring device.\n";
			return nullptr;
		}
		else
			return factory->second(_id, _data);
	}

}	// namespace dmc