////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "hueLight.h"
#include <iostream>
#include <string>

using namespace std;

namespace dmc { namespace hue {

	// Statid data definition
	Bridge* Light::sBridge = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	Light::Light(unsigned _id, const std::string& _name, const std::string& _hueId)
		:Device(_id, _name)
		,Actuator(_id,_name)
		,Sensor(_id,_name)
		, mHueId(_hueId)
	{
		if(!sBridge)
			Bridge::load();
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Light::runCommand(const Json&) {
		if(!sBridge)
			return false;
		std::cout << "Hue light received a command\n";
		string commandUrl = string("lights/") + mHueId + "/state";
		Json commandBody(R"({"on":True)");
		return sBridge->putData(commandUrl, commandBody);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Light::read(const std::string& _param, Json& _dst) {
		std::cout << "requested param \"" << _param << "\" of Hue light\n";
		return true;
	}

}}	// namespace dmc::hue