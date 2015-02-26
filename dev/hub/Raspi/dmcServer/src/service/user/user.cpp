////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "user.h"
#include <cassert>
#include <core/comm/http/httpServer.h>
#include <core/comm/http/response/jsonResponse.h>
#include <core/comm/http/response/response404.h>
#include <core/comm/http/response/response200.h>
#include <string>
#include <provider/deviceMgr.h>
#include <home/device/actuator.h>

using namespace std;

namespace dmc {

	using namespace http;

	const string User::cDeviceLabel = "/device";

	//------------------------------------------------------------------------------------------------------------------
	User::User(const Json& _userData, Server* _serviceToListen, DeviceMgr* _devMgr)
		:mDevices(_devMgr)
	{
		// Init internal data
		mName = _userData["name"].asText();
		mId = _userData["id"].asText();
		mPrefix = string("/user/") + mId;
		// Register to service
		_serviceToListen->setResponder(mPrefix, [this](Server* _s, unsigned _conId, const Request& _request){
			this->processRequest(_s, _conId, _request);
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	void User::processRequest(Server* _s, unsigned _conId, const Request& _request) {
		string url = _request.url();
		// Extract command
		string command = extractCommand(_request.url());
		// Dispatch command
		Response* response = runCommand(command, _request);
		assert(response);
		_s->respond(_conId, *response);
		delete response;
	}

	//------------------------------------------------------------------------------------------------------------------
	Response* User::runCommand(const std::string& _cmd, const http::Request& _request) const {
		if(_cmd.empty()) { // Request state
			return new Response200("666 TODO: Show list of devices and rooms available to the user\n");
		} else {
			unsigned devLabelSize = cDeviceLabel.size();
			// Extract device id
			if(_cmd == cDeviceLabel) {
				return new Response404("404: Device list not available");
			}
			else if(_cmd.substr(0,devLabelSize) == cDeviceLabel) {
				return deviceCommand(_cmd.substr(devLabelSize), _request);
			}
			else
				return new Response404(string("User unable to run command ") + _cmd);
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Response* User::deviceCommand(const std::string& _cmd, const http::Request& _request) const {
		string devIdStr = _cmd.substr(1); // Discard initial '/'
		unsigned devId = strtol(devIdStr.c_str(), nullptr, 16);
		Device* dev = mDevices->device(devId);
		if(!dev){
			return new Response404(string("Error 404: Device ")+devIdStr+" not found\n");
		}
		// Execute request
		switch (_request.method())
		{
		case Request::METHOD::Get:
			return new JsonResponse(dev->read(Json(_request.body())));
		case Request::METHOD::Put: {
			// Use device as an actuator
			Actuator* act = dynamic_cast<Actuator*>(dev);
			if(act) {
				Json body(_request.body()); // Extract body from request
				return new JsonResponse(act->runCommand(body));
			} else {
				return new Response404(string("Error 404: Device ")+devIdStr+" is not an actuator\n");
			}
		}
		default:
			return new Response404("Error 404: Unsupported http method");
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	string User::extractCommand(const std::string& _url) const {
		// Format string
		assert(!_url.empty() && _url[0] == '/');
		string formatedUrl = _url;
		if(_url[_url.size() - 1] == '/')
			formatedUrl.pop_back();
		// Remove prefix
		assert((formatedUrl.size() >= mPrefix.size()) && (formatedUrl.substr(0, mPrefix.size()) == mPrefix));
		return formatedUrl.substr(mPrefix.size());
	}

}	// namespace dmc