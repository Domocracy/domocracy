////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "kodi.h"

namespace dmc { namespace kodi {

	//------------------------------------------------------------------------------------------------------------------
	Kodi::Kodi(const Json& _data) 
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		mPort = (unsigned)_data["port"].asInt();
		mIp = _data["ip"].asText();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::runCommand(const Json&) {
		return true;
	}

}}	// namespace dmc::kodi