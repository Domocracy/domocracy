////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PROVIDER_PERSISTENCE_H_
#define _DMCSERVER_PROVIDER_PERSISTENCE_H_

#include <core/comm/json/json.h>
#include <string>

namespace dmc {

	class Persistence {
	public:
		static Persistence* get		() { return sInstance; }
		static void			init	();
		static void			end		() { delete sInstance; sInstance = nullptr; }

		void				saveData(const std::string& _dataId, const Json& _data);
		Json				getData	(const std::string& _dataId);

	private:
		Persistence() = default;
		~Persistence() = default;
		static Persistence* sInstance;
	};

}	// namespace dmc

#endif // _DMCSERVER_PROVIDER_PERSISTENCE_H_