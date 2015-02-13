////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_DMCSERVER_H_
#define _DMCSERVER_DMCSERVER_H_

#include <core/comm/http/httpServer.h>
#include <string>
#include <vector>
#include <service/public/hubInfo.h>

namespace dmc {

	class PublicService;
	class User;
	class DeviceMgr;
	
	class DmcServer {
	public:
		DmcServer	(int _argc, const char** _argv);
		~DmcServer();
		// Return false means the application should exit
		bool update	();

	private:
		void processArguments	(int _argc, const char** _argv);
		void loadUsers			(const std::string& _fileName);

		// Config
		unsigned mHttpPort = 80;

		// Components
		http::Server*		mWebServer = nullptr;
		PublicService*		mPublicService = nullptr;
		std::vector<User*>	mUsers;
		HubInfo*			mInfo = nullptr;
		DeviceMgr*			mDeviceMgr = nullptr;
	};

}

#endif // _DMCSERVER_DMCSERVER_H_