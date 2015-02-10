////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include <cassert>
#include "dmcServer.h"

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::DmcServer(int _argc, const char** _argv)
	{
		processArguments(_argc, _argv); // Execution arguments can override default configuration values
		// Prerequisites for launching the service
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
	DmcServer::~DmcServer(){
		// Prerequisites for launching the service
		// if(mService)
		// 	delete mService;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool DmcServer::update() {
		// assert(mService);
		// mService->update();
		return true;
	}
}