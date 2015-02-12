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

namespace dmc {

	class Device;

	class DeviceMgr {
	public:
		DeviceMgr();
		Device* get(unsigned _id) const;
	private:
		std::unordered_map<unsigned,Device*>	mDevices;

	};

}	// namespace dmc

#endif // _DMCSERVER_PROVIDERS_DEVICEMGR_H_