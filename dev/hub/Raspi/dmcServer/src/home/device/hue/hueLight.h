////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_HUE_HUELIGHT_H_
#define _DMCSERVER_HOME_DEVICE_HUE_HUELIGHT_H_

#include <home/device/actuator.h>
#include <home/device/sensor.h>

namespace dmc { namespace hue {

	class Light : public Actuator, public Sensor {
	public:
		Light(unsigned _id, const std::string& _name, const std::string& _hueId);

		// Device interface
		bool runCommand(const Json& _cmd) override;
		bool read(const std::string& _param, Json& _dst) override;

	private:
		std::string mHueId;
	};

}}	// namespace dmc::hue

#endif // _DMCSERVER_HOME_DEVICE_HUE_HUELIGHT_H_