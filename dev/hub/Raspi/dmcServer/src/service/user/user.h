////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_USER_USER_H_
#define _DMCSERVER_USER_USER_H_

#include <core/comm/json/json.h>

namespace dmc {

	namespace http { class Server; }

	class DeviceMgr;

	class User {
	public:
		User(const Json& _userData, http::Server* _serviceToListen, DeviceMgr*);

	private:
		void		processRequest	(Server* _s, unsigned _conId, const Request& _request);
		std::string extractCommand	(const std::string& _url) const;

		std::string mName;
		std::string mId;
		std::string mPrefix;
		DeviceMgr*	mDevices;
	};

}	// namespace dmc

#endif // _DMCSERVER_USER_USER_H_