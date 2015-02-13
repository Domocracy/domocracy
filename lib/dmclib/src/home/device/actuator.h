////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_ACTUATOR_H_
#define _DMCSERVER_HOME_DEVICE_ACTUATOR_H_

#include "../device.h"

namespace dmc {

	class Json;

	class Actuator : public virtual Device {
	public:
		Actuator(unsigned _id, const std::string& _name) : Device(_id, _name) {}
		virtual bool runCommand(const Json& _cmd) = 0;
	};
}

#endif // _DMCSERVER_HOME_DEVICE_ACTUATOR_H_