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
	Scene::Scene(unsigned _id, const Json& _data)
		:Actuator(_id, _data["name"].asText())
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
	Json Scene::runCommand(const Json&) {
		DeviceMgr* mgr = DeviceMgr::get();
		bool ok = true;
		for(auto i : mChildren)
		{
			Actuator* act = dynamic_cast<Actuator*>(mgr->device(i.first));
			if(!act){
				ok = false;
				continue;
			}
			ok &= (act->runCommand(i.second)["result"].asText() == "ok");
		}
		return ok?Json(R"("result":"ok")"):Json(R"("result":"fail")");
	}

	//------------------------------------------------------------------------------------------------------------------
	Json* Scene::serialize() const {
		Json& base = *Actuator::serialize();
		base["type"].setText("Scene");
		Json children("[]"); // Serialize children commands
		for (auto child : mChildren) {
			Json& childData = *new Json("{}");
			childData["id"].setInt(child.first);
			childData["cmd"] = child.second;
			children.asList().push_back(&childData);
		}
		base["children"] = children;
		return &base;
	}
}
