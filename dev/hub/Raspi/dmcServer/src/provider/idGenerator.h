////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Mar/03
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PROVIDER_IDGENERATOR_H_
#define _DMCSERVER_PROVIDER_IDGENERATOR_H_

#include <cstdint>
#include <set>

namespace dmc {

	class IdGenerator {
	public:
		static void init();
		static void end();
		static IdGenerator* get();

		uint32_t newId();

	private:
		IdGenerator();
		~IdGenerator();

		std::set<uint32_t>	mGeneratedIds;
		static IdGenerator* sInstance;
	};

}	// namespace dmc

#endif // _DMCSERVER_PROVIDER_IDGENERATOR_H_