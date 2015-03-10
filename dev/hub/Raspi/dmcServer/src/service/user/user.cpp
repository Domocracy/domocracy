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
#include <core/comm/http/httpResponse.h>
#include <string>
#include <provider/deviceMgr.h>
#include <home/device/actuator.h>
#include <sstream>

using namespace std;

namespace dmc {

	using namespace http;

	const string User::cDeviceLabel = "/device";
	const string User::cAddDevLabel = "/addDevice";

	//------------------------------------------------------------------------------------------------------------------
	User::User(const Json& _userData, Server* _serviceToListen)
	{
		// Init internal data
		mName = _userData["name"].asText();
		mId = _userData["id"].asText();
		mPrefix = string("/user/") + mId;
		loadDevices(_userData["devices"]); // Ids of devices available to this user
		// Register to service
		_serviceToListen->setResponder(mPrefix, [this](Server* _s, unsigned _conId, const Request& _request){
			this->processRequest(_s, _conId, _request);
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	User::User(unsigned _userId, Server* _serviceToListen) {
		mId = idAsString(_userId);
		mName = mId;
		mPrefix = string("/user/") + mId;
		// Register to service
		_serviceToListen->setResponder(mPrefix, [this](Server* _s, unsigned _conId, const Request& _request){
			this->processRequest(_s, _conId, _request);
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	string User::idAsString(unsigned _id) {
		stringstream idStream;
		idStream.setf(ios_base::hex);
		idStream.width(8);
		idStream.fill('0');
		idStream << _id;
		return idStream.str();
	}

	//------------------------------------------------------------------------------------------------------------------
	void User::processRequest(Server* _s, unsigned _conId, const Request& _request) {
		string url = _request.url();
		// Extract command
		string command = extractCommand(_request.url());
		// Dispatch command
		_s->respond(_conId, runCommand(command, _request));
	}

	//------------------------------------------------------------------------------------------------------------------
	Response User::runCommand(const std::string& _cmd, const http::Request& _request) {
		if(_cmd.empty()) { // Request state
			return reportUserData();
		} else {
			// Extract device id
			if(_cmd == cDeviceLabel) {
				return Response::response404("404: Device list not available");
			}
			else if(_cmd.substr(0,cDeviceLabel.size()) == cDeviceLabel) {
				return deviceCommand(_cmd.substr(cDeviceLabel.size()), _request);
			} else if(_cmd == cAddDevLabel) {
				return addDevice(Json(_request.body()));
			}
			else
				return Response::response404(string("User unable to run command ") + _cmd);
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Response User::addDevice(const Json& _deviceData) {
		Device* newDev = DeviceMgr::get()->newDevice(_deviceData["type"], _deviceData);
		if(newDev) {
			Json result (R"({"result":"ok")");
			result["id"].setInt((int)newDev->id());
			return Response::jsonResponse(result);
		} else
			return Response::response200(R"({"result":"fail", "error":"unable to create device"})");
	}

	//------------------------------------------------------------------------------------------------------------------
	Response User::deviceCommand(const std::string& _cmd, const http::Request& _request) const {
		string devIdStr = _cmd.substr(1); // Discard initial '/'
		char* idEnd;
		unsigned devId = strtol(devIdStr.c_str(), &idEnd, 16);
		Device* dev = nullptr;
		if(mDevices.find(devId) != mDevices.end()) { // Device available to the user, permission granted
			dev = DeviceMgr::get()->device(devId);
		}
		unsigned idLen = idEnd-devIdStr.c_str();
		if(!dev){
			return Response::response404(string("Error 404: Device ")+devIdStr+" not found\n");
		}
		// Execute request
		switch (_request.method())
		{
		case Request::METHOD::Get: {
			Json request("{}");
			request["cmd"].setText(devIdStr.substr(idLen+1));
			return Response::jsonResponse(dev->read(request));
		}
		case Request::METHOD::Put: {
			// Use device as an actuator
			Actuator* act = dynamic_cast<Actuator*>(dev);
			if(act) {
				Json body(_request.body()); // Extract body from request
				return Response::jsonResponse(act->runCommand(body));
			} else {
				return Response::response404(string("Error 404: Device ")+devIdStr+" is not an actuator\n");
			}
		}
		default:
			return Response::response404("Error 404: Unsupported http method");
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	Response User::reportUserData() const {
		Json userData("{}");
		userData["devices"] = Json("{}");
		userData["rooms"] = Json("{}");
		return Response::jsonResponse(userData);
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

	//------------------------------------------------------------------------------------------------------------------
	void User::loadDevices(const Json& _deviceList) {
		for(auto entry : _deviceList.asList()) {
			mDevices.insert((unsigned)entry->asInt());
		}
	}


}	// namespace dmc