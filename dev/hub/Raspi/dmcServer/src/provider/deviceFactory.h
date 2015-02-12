////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PROVIDERS_DEVICEFACTORY_H_
#define _DMCSERVER_PROVIDERS_DEVICEFACTORY_H_

#include <string>

namespace dmc {

	class Device;
	class Json;

	class DeviceFactory {
	public:
		DeviceFactory();

		Device* create(const std::string& _devType, const Json& _constructionData);
	};

}	// namespace dmc

#endif // _DMCSERVER_DEVICES_DEVICEFACTORY_H_