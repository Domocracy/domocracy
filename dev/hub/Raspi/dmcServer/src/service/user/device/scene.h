////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
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
		Scene(unsigned _id, const Json& _data);
		~Scene() = default;

		Json	runCommand	(const Json& _cmd) override;
		Json*	serialize	() const override;

	private:
		std::unordered_map<unsigned,Json>	mChildren;
		Json								mPanels;
	};
}

#endif // _DMCSERVER_SERVICE_USER_DEVICE_H_