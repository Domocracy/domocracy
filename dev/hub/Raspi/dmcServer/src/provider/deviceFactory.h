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
#include <unordered_map>
#include <functional>

namespace dmc {

	class Factory;
	class Device;
	class Json;

	class DeviceFactory {
	public:
		DeviceFactory();

		Device* create(unsigned _id, const std::string& _devType, const Json& _constructionData);
		typedef std::function<Device*(unsigned _id, const Json&)>	Creator;

	private:
		std::unordered_map<std::string, Creator>	mFactories;
	};

}	// namespace dmc

#endif // _DMCSERVER_DEVICES_DEVICEFACTORY_H_