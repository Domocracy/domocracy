////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include <cassert>
#include "dmcServer.h"
#include <core/comm/json/json.h>
#include <core/time/time.h>
#include <public/publicService.h>
#include <core/comm/http/response/response200.h>
#include <service/user/user.h>
#include <provider/deviceMgr.h>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::DmcServer(int _argc, const char** _argv)
	{
		processArguments(_argc, _argv); // Execution arguments can override default configuration values
		// Launch web service
		mWebServer = new http::Server(mHttpPort);
		mWebServer->setResponder("/public/ping", http::Response200());
		mInfo = new HubInfo(mWebServer);
		mPublicService = new PublicService(mWebServer);
		mDeviceMgr = new DeviceMgr();
		loadUsers("users.json");
	}

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::~DmcServer(){
		for(auto user : mUsers)
			delete user;
		if(mPublicService)
			delete mPublicService;
		if(mWebServer)
			delete mWebServer;
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::processArguments(int _argc, const char** _argv) {
		for(int i = 0; i < _argc; ++i) {
			std::string argument(_argv[i]);
			if(argument.substr(0,9)=="-httpPort=") {
				mHttpPort = atoi(argument.substr(9).c_str());
			}
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::loadUsers(const std::string&) {
		Json usersDatabase = Json(R"([{"name":"dmc64", "id":"dmc64"}])"); // Hardcoded user
		for(auto userData : usersDatabase.asList()) {
			mUsers.push_back(new User(*userData, mWebServer, mDeviceMgr));
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	bool DmcServer::update() {
		// assert(mService);
		Time::get()->sleep(30);
		// mService->update();
		return true;
	}
}