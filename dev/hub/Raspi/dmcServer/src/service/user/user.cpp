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

	//------------------------------------------------------------------------------------------------------------------
	User::User(const Json& _userData, Server* _serviceToListen, DeviceMgr* _devMgr)
		:mDevices(_devMgr)
	{
		// Init internal data
		mName = _userData["name"].asText();
		mId = _userData["id"].asText();
		mPrefix = (string("/user/") + mId + "/").size();
		// Register to service
		_serviceToListen->setResponder(string("/dev/") + mName, [this](Server* _s, unsigned _conId, const Request& _request){
			this->processRequest(_s, _conId, _request);
		});
	}

	//------------------------------------------------------------------------------------------------------------------
	void User::processRequest(Server* _s, unsigned _conId, const Request& _request) {
		string url = _request.url();
		// Extract command
		string command = extractCommand(_request.url());
		// Dispatch command
			if(command.empty()) // Request state
			{
				Response200 r;
				r.setBody("666 TODO: Show list of devices and rooms available to the user\n");
				_s->respond(_conId, r);
				return;
			}
			else {
				// Extract device id
				assert(command.substr(0,5) == "/dev/");
				unsigned devId = unsigned(atoi(command.substr(5).c_str()));
				Device* dev = mDevices->get(devId);
				// Execute request
				switch (_request.method())
				{
				case Request::METHOD::Get:
					_s->respond(_conId, Response404());
					break;
				default:
					break;
				}
			}
			string command = url.substr(mPrefixSize);
			Actuator* device = dynamic_cast<Actuator*>(mDevices->get(42));
			if(!device)
				_s->respond(_conId, Response404());
			if(command == "on")
			{

				device->runCommand(Json(R"({"on":true})"));
				_s->respond(_conId, Response200());
			} else if (command == "off")
			{
				device->runCommand(Json(R"({"on":false})"));
				_s->respond(_conId, Response200());
			} else
				_s->respond(_conId, Response404());
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