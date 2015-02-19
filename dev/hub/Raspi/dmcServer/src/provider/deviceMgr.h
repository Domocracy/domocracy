////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PROVIDERS_DEVICEMGR_H_
#define _DMCSERVER_PROVIDERS_DEVICEMGR_H_

#include <string>
#include <unordered_map>
#include "deviceFactory.h"

namespace dmc {

	class Device;

	class DeviceMgr {
	public:
		// Singleton interface
		static void init();
		static void end();
		static DeviceMgr* get();

		Device* device(unsigned _id) const;

	private:
		DeviceMgr();
		std::unordered_map<unsigned,Device*>	mDevices;
		DeviceFactory							mFactory;

		static DeviceMgr* sInstance;
	};

}	// namespace dmc

#endif // _DMCSERVER_PROVIDERS_DEVICEMGR_H_