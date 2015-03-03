////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Mar/03
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "idGenerator.h"
#include <cassert>
#include <ctime>
#include <cstdlib>
#include "persistence.h"

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	// Static data definition
	//------------------------------------------------------------------------------------------------------------------
	IdGenerator* IdGenerator::sInstance = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	void IdGenerator::init() {
		assert(!sInstance);
		sInstance = new IdGenerator();
	}

	//------------------------------------------------------------------------------------------------------------------
	IdGenerator* IdGenerator::get() {
		return sInstance;
	}

	//------------------------------------------------------------------------------------------------------------------
	void IdGenerator::end() {
		assert(sInstance);
		delete sInstance;
		sInstance = nullptr;
	}

	//------------------------------------------------------------------------------------------------------------------
	uint32_t IdGenerator::newId() {
		for(;;) {
			uint32_t id = (uint32_t)rand();
			if(mGeneratedIds.find(id) == mGeneratedIds.end()) { // New key, not found
				mGeneratedIds.insert(id);
				return id;
			}
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	IdGenerator::IdGenerator() {
		// Set random seed
		srand((uint32_t)time(0));
		// Load previous data
		Json storedIds = Persistence::get()->getData("randomIds");
		for(auto id : storedIds.asList()) {
			mGeneratedIds.insert((uint32_t)id->asInt());
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	IdGenerator::~IdGenerator() {
		// Save generated ids
		Json ids("[]");
		for(auto id : mGeneratedIds) {
			Json *idJson = new Json();
			idJson->setInt(id);
			ids.asList().push_back(idJson);
		}
	}

}	// namespace dmc
