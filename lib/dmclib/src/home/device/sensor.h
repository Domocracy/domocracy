////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_SENSOR_H_
#define _DMCSERVER_HOME_DEVICE_SENSOR_H_

#include "../device.h"
#include <string>

namespace dmc {

	class Json;

	class Sensor : public virtual Device {
	public:
		virtual bool read(const std::string& _param, Json& _dst) = 0;
	};
}

#endif // _DMCSERVER_HOME_DEVICE_SENSOR_H_