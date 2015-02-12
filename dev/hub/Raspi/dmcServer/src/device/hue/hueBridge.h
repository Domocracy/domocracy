////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/12
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_HUE_HUEBRIDGE_H_
#define _DMCSERVER_HOME_DEVICE_HUE_HUEBRIDGE_H_

#include <core/comm/json/json.h>
#include <string>

namespace dmc { namespace hue {

	class Bridge {
	public:
		static	Bridge*	load	();
				Json	getData	(const std::string& _url);
				bool	putData(const std::string& _url, const Json& _data);

	private:
	};

}}	// namespace dmc::hue

#endif // _DMCSERVER_HOME_DEVICE_HUE_HUELIGHT_H_