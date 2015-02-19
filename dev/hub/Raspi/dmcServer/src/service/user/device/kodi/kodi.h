////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_
#define _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_

#include <string>
#include <core/comm/json/json.h>
#include <home/device/actuator.h>

namespace dmc { namespace kodi {

	class Kodi final : public Actuator{
	public:
		Kodi(const Json&);
	};

}}	// namespace dmc::kodi

#endif // _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_