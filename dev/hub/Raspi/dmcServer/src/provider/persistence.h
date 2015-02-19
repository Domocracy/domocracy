////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PROVIDER_PERSISTENCE_H_
#define _DMCSERVER_PROVIDER_PERSISTENCE_H_

#include <core/comm/json/json.h>
#include <string>

namespace dmc {

	class Persistence {
	public:
		static Persistence* get		();
		static void			init	();

		void				saveData(const std::string& _dataId, const Json& _data);
		Json				getData	(const std::string& _dataId);

	private:
		Persistence();
	};

}	// namespace dmc

#endif // _DMCSERVER_PROVIDER_PERSISTENCE_H_