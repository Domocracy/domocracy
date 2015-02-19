////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "scene.h"
#include <provider/deviceMgr.h>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	Scene::Scene(const Json& _data)
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		Json childrenData = _data["children"];
		const auto& list = childrenData.asList();
		for(size_t i = 0; i < list.size(); ++i) {
			unsigned childId = unsigned((*list[i])["id"].asInt());
			Json childCmd = (*list[i])["cmd"];
			mChildren.insert(std::make_pair(childId, childCmd));
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Scene::runCommand(const Json&) {
		DeviceMgr* mgr = DeviceMgr::get();
		bool ok = true;
		for(auto i : mChildren)
		{
			Actuator* act = dynamic_cast<Actuator*>(mgr->device(i.first));
			if(!act){
				ok = false;
				continue;
			}
			ok &= act->runCommand(i.second);
		}
		return ok;
	}
}