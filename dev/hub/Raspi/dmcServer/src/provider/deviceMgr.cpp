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
#include "persistence.h"
#include <cassert>
#include <iostream>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DeviceMgr* DeviceMgr::sInstance = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	void DeviceMgr::init() {
		assert(!sInstance);
		sInstance = new DeviceMgr;
	}

	//------------------------------------------------------------------------------------------------------------------
	void DeviceMgr::end() {
		if(sInstance) {
			delete sInstance;
			sInstance = nullptr;
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	DeviceMgr* DeviceMgr::get() {
		return sInstance;
	}

	//------------------------------------------------------------------------------------------------------------------
	DeviceMgr::DeviceMgr() {
		// Load devices from local database
		Json factoriesData = Persistence::get()->getData("devices");
		if(factoriesData.isNill())
			return;
		for(size_t i = 0; i < factoriesData.asList().size(); ++i)
		{
			const Json& data = *factoriesData.asList()[i];
			Device* dev = mFactory.create(data["type"].asText(), data["data"]);
			if(!dev)
				continue;
			if(mDevices.find(dev->id()) != mDevices.end()) {
				std::cout << "Warning: Duplicate device id (" << dev->id() << ").\nOld device will be overwriten\n";
			}
			mDevices.insert(std::make_pair(dev->id(), dev));
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceMgr::device(unsigned _id) const {
		auto iter = mDevices.find(_id);
		if(iter != mDevices.end())
			return iter->second;
		else
			return nullptr;
	}

}	// namespace dmc
