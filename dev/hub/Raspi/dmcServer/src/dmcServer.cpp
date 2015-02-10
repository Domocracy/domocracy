////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include <core/comm/socket/socket.h>
#include "service/lanService.h" // This include must be first due to windows-specific include order requirements
#include <cassert>
#include "dmcServer.h"
#include "peripherals/plc/plcDriver.h"
#include <core/comm/json/json.h>
#include <core/comm/http/response/htmlResponse.h>
#include <core/comm/http/response/jsonResponse.h>
#include <core/comm/http/httpServer.h>
#include <device/deviceMgr.h>
#include <service/scan/deviceScanner.h>

using namespace dmc::http;

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	DmcServer::DmcServer(int _argc, const char** _argv)
		:mTopPin("8")
		//,mService(nullptr)
	{
		loadDefaultConfig(); // This fills every important thing with default values
		processArguments(_argc, _argv); // Execution arguments can override default configuration values
		// Prerequisites for launching the service
		initHardware();
		loadDatabase();
		// Actually start the service
		launchService();
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::processArguments(int _argc, const char** _argv) {
		for(int i = 0; i < _argc; ++i) {
			std::string argument(_argv[i]);
			if(argument.substr(0,9)=="-plcPort=") {
				mPlcPortName = argument.substr(9);
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

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::loadDefaultConfig() {
		mPlcPortName = "/dev/ttyAMA0";
		
		/*In order to disable serial port login, we need to log in via ssh into the raspi, and then look for the file
		/etc/inititab. There we need to comment the line T0:23:respawn:/sbin/getty -L ttyAMA0 115200 vt100.
		After that, we can disable the bootup info, it's optional because maybe you'll like to know what is happening there.
		Whatever you decide, you need to edit the file /boot/cmdline.txt and remove all the references to the serial port.*/
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::initHardware() {
		PLCDriver::init(mPlcPortName.c_str());
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::loadDatabase() {
		DeviceMgr::get()->loadDatabase();
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::launchService() {
		mTopPin.outPut();
		mTopPin.setHigh();
		// mService = LANService::get();
		mServer = new http::Server(5028);

		// Load hue bridges
		//loadHueBridges();

		runBuildService();
		runDeviceService();
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::loadHueBridges() {
		// Open bridges file
		Json storedBridges = storedBridges.openFromFile("hueBridges.json");
		for(const auto& bridge : storedBridges.asList()) {
			// Bridges register themselves automatically
			new HueBridge(*bridge);
			// Load devices from this bridge
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::runBuildService() {
		mServer->setResponder("/", [=](Server* _s, unsigned _con, const http::Request&) {
			// Send form
			_s->respond(_con, HtmlResponse("IDE/editor.html"));
		});
		mServer->setResponder("/build", [=](Server* _s, unsigned _con, const http::Request&) {
			// Send form
			_s->respond(_con, HtmlResponse("IDE/editor.html"));
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	void DmcServer::runDeviceService() {
		mServer->setResponder("/dev/on", [=](Server* _s, unsigned _con, const http::Request&) {
			//DeviceMgr::get()->getDevice({1,0,0,44})->runCommand(Json("{\"cmd\":\"on\"}"));
			_s->respond(_con, JsonResponse(Json("True")));
		});

		mServer->setResponder("/dev/off", [=](Server* _s, unsigned _con, const http::Request&) {
			//DeviceMgr::get()->getDevice({1,0,0,44})->runCommand(Json("{\"cmd\":\"off\"}"));
			_s->respond(_con, JsonResponse(Json("True")));
		});
	}
}