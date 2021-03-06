////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include <cassert>
#include "dmcServer.h"
#include <core/comm/json/json.h>
#include <core/time/time.h>
#include <core/comm/http/httpResponse.h>
#include <service/user/user.h>
#include <provider/deviceMgr.h>
#include <provider/persistence.h>
#include <provider/idGenerator.h>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::DmcServer(int _argc, const char** _argv)
	{
		processArguments(_argc, _argv); // Execution arguments can override default configuration values
		
		// Launch web service
		Persistence::init();

		IdGenerator::init();
		mWebServer = new http::Server(mHttpPort);
		mInfo = new HubInfo(mWebServer);
		DeviceMgr::init();
		loadUsers();
		// Public services
		mWebServer->setResponder("/public/ping", http::Response::response200());
		mWebServer->setResponder("/public/newUser", [this](http::Server* _s, unsigned _conId, const http::Request&) {
			_s->respond(_conId, createNewUser());
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::~DmcServer(){
		for(auto user : mUsers)
			delete user;
		DeviceMgr::end();
		if(mWebServer)
			delete mWebServer;
		IdGenerator::end();
		Persistence::end();
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
	void DmcServer::loadUsers() {
		Json usersDatabase = Persistence::get()->getData("users");
		if(usersDatabase.isNill())
			return;
		for(auto userData : usersDatabase.asList()) {
			mUsers.push_back(new User(*userData, mWebServer));
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	http::Response DmcServer::createNewUser() {
		// Generate unique key
		unsigned newId = IdGenerator::get()->newId();
		// Store user locally
		User* u = new User(newId, mWebServer);
		mUsers.push_back(u);
		// Return generated credentials
		Json result("{}");
		result["result"].setText("ok");
		result["userId"].setText(u->strId());
		return http::Response::jsonResponse(result);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool DmcServer::update() {
		// assert(mService);
		Time::get()->sleep(30);
		// mService->update();
		return true;
	}
}