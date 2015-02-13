////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "hueLight.h"
#include <iostream>

namespace dmc { namespace hue {

	//------------------------------------------------------------------------------------------------------------------
	Light::Light(unsigned _id, const std::string& _name, const std::string& _hueId)
		:Device(_id, _name)
		,Actuator(_id,_name)
		,Sensor(_id,_name)
		, mHueId(_hueId)
	{
		// 
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Light::runCommand(const Json&) {
		std::cout << "Hue light received a command\n";
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Light::read(const std::string& _param, Json& _dst) {
		std::cout << "requested param \"" << _param << "\" of Hue light\n";
		return true;
	}

}}	// namespace dmc::hue