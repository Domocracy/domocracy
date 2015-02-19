////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "persistence.h"
#include <cassert>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	Persistence* Persistence::sInstance = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	void Persistence::init() {
		assert(!sInstance);
		sInstance = new Persistence();
	}

	//------------------------------------------------------------------------------------------------------------------
	void Persistence::saveData(const std::string& _dataId, const Json& _data) {
		_data.saveToFile(_dataId + ".json");
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Persistence::getData(const std::string& _dataId) {
		return Json::openFromFile(_dataId + ".json");
	}

}	// namespace dmc