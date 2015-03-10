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
#include <provider/idGenerator.h>

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
	Device* DeviceMgr::device(unsigned _id) const {
		auto iter = mDevices.find(_id);
		if(iter != mDevices.end())
			return iter->second;
		else
			return nullptr;
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceMgr::newDevice(const Json& _devType, const Json& _devData) {
		unsigned devId = IdGenerator::get()->newId();
		save();
		return createDevice(devId, _devType, _devData);
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
			loadDevice(data);
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Device* DeviceMgr::createDevice(unsigned _id, const Json& _devType, const Json& _devData) {
		if(mDevices.find(_id) != mDevices.end()) {
			std::cout << "Warning: Duplicate device id (" << _id << ").\nOld device will be overwriten\n";
		} 
		
		Device* newDev = mFactory.create(_id, _devType.asText(), _devData);
		mDevices.insert(std::make_pair(_id, newDev));
		return newDev;
	}

	//------------------------------------------------------------------------------------------------------------------
	void DeviceMgr::loadDevice(const Json& _creationData){
		unsigned devId = (unsigned)_creationData["id"].asInt();
		createDevice(devId, _creationData["type"], _creationData["data"]);
	}

	//------------------------------------------------------------------------------------------------------------------
	void DeviceMgr::save() {
		Json deviceList("[]");
		for(auto dev : mDevices) {
			Json *devData = dev.second->serialize();
			assert(!(*devData)["type"].isNill());
			deviceList.asList().push_back(devData);
		}
		Persistence::get()->saveData("devices", deviceList);
	}

}	// namespace dmc
