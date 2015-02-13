////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "deviceMgr.h"
#include "deviceFactory.h"
#include <core/comm/json/json.h>
#include <home/device.h>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DeviceMgr::DeviceMgr() {
		// Load devices from local database
		Device* sampleLight = mFactory.create("HueLight", Json(R"({"name":"HueLight1", "id":42, "data":{"id":"2"}})"));
		mDevices.insert(std::make_pair(sampleLight->id(), sampleLight));
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceMgr::get(unsigned _id) const {
		auto iter = mDevices.find(_id);
		if(iter != mDevices.end())
			return iter->second;
		else
			return nullptr;
	}

}	// namespace dmc
