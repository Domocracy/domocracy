////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_SERVICE_USER_DEVICE_H_
#define _DMCSERVER_SERVICE_USER_DEVICE_H_

#include <home/device/actuator.h>
#include <core/comm/json/json.h>
#include <unordered_map>

namespace dmc {

	class Json;

	class Scene final : public Actuator {
	public:
		Scene(const Json& _data);
		~Scene() = 0;

		bool runCommand(const Json& _cmd) override;

	private:
		std::unordered_map<unsigned,Json>	mChildren;
	};
}

#endif // _DMCSERVER_SERVICE_USER_DEVICE_H_