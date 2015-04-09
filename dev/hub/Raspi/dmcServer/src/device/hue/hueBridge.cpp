////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/12
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "hueBridge.h"
#include "../../provider/persistence.h"

#include <core/comm/http/httpClient.h>
#include <cassert>
#include <iostream>
#include <core/comm/http/httpRequest.h>
#include <core/time/time.h>

using namespace dmc::http;

namespace dmc { namespace hue {
	//------------------------------------------------------------------------------------------------------------------
	Bridge *Bridge::sBridge = nullptr;

	//------------------------------------------------------------------------------------------------------------------
	Bridge* Bridge::get(){
		if (sBridge == nullptr){
			init();
		}
		return sBridge;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Bridge::init() {
		Json bridgesData = Persistence::get()->getData("hue");
		if (!bridgesData.isNill() && bridgesData.asList().size() != 0){
			sBridge = new Bridge(*bridgesData.asList()[0]);		// 666 Check more bridges.
		}
		else{
			std::string ip = queryLocalIp();
			if (ip.size() != 0){
				Json bridgeData("{\"internalipaddress\":\"" + ip + "\", \"username\":\"domocracy64\"}");
				Persistence::get()->saveData("hue", bridgeData);
				sBridge = new Bridge(bridgeData);
			}
			else {
				std::cout << "Could not connect to hue bridge" << std::endl;
			}
		}

	}

	//------------------------------------------------------------------------------------------------------------------
	Json Bridge::getData	(const std::string& _url) {
		http::Request req = http::Request(http::Request::Get, "/api/" + mUsername + "/" +_url, "");
		mConn->connect(mLocalIp);
		http::Response* result = mConn->makeRequest(req);
		Json bridgeData(result->body());

		return bridgeData;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Bridge::putData(const std::string& _url, const Json& _data) {
		assert(_url.size() > 1 && _url[0] == '/');
		if(mState == State::disconnected)
			return false;
		Request command = Request::jsonRequest(Request::METHOD::Put, std::string("/api/")+mUsername+_url, _data);
		command.headers()["Host"] = mLocalIp;
		http::Response* result = mConn->makeRequest(command);
		return result != nullptr;
	}

	//------------------------------------------------------------------------------------------------------------------
	Bridge::Bridge(const Json& _info) {
		mState = State::connecting;
		// Extract basic data from the json object
		mLocalIp = _info["internalipaddress"].asText();
		mUsername = _info["username"].asText();
		// Connect to the bridge
		mConn = new http::Client;
		mConn->connect(mLocalIp);
		// Request up to date data from the bridge

		mInitThread = std::thread([this,_info]() 
		{
			http::Request req = http::Request(http::Request::Get, "/api/" + mUsername , "" );
			req.headers()["Host"] = mLocalIp;
			req.headers()["Content-Type"] = "text/plain;charset=UTF-8";
			req.headers()["Content-Length"]="0";
			http::Response* result = mConn->makeRequest(req);
			if(!result)
			{
				mState = State::disconnected;
				std::cout << "Unable to connect to Hue bridge\n";
				return;
			}
			Json data(result->body());
			if (data.isList() && data[0].contains("error") && data[0]["error"]["type"].asInt() == 1){	// Error, register new user
				std::cout << "User is not registered. Registering: " << mUsername << std::endl;
				registerUser();
			}

			std::cout << "Connected to Hue bridge\n";

			mState = State::connected;
		});
	}


	//------------------------------------------------------------------------------------------------------------------
	std::string Bridge::queryLocalIp(){
		dmc::Socket hueService;
		hueService.open("localhost", 5028);

		hueService.write("ip\n");
		const int MaxLength = 1024;
		char msg[MaxLength];

		if (hueService.read(msg, MaxLength) <= 0){
			std::cout << "Failed connection with local Hue Service" << std::endl;
			return "";
		}

		std::string ip(msg);
		return ip.substr(0, ip.find("\r\n"));
	}

	//------------------------------------------------------------------------------------------------------------------
	void Bridge::registerUser() {
		http::Request req = http::Request(http::Request::Post,
			"/api",
			"{\"devicetype\":\"domocracy_hub\", \"username\":\""+ mUsername +"\"}");

		for (unsigned tries = 0 ; tries < 5; tries ++){
			mConn->connect(mLocalIp);
			http::Response* result = mConn->makeRequest(req);
			Json data(result->body());

			if (data[0].contains("error")){
				std::cout << "Press Hue's link button" << std::endl;
			}
			else if (data[0].contains("success")){
				std::cout << "User registered in Hue Bridge" << std::endl;
				break;
			}
			dmc::Time::get()->sleep(5);
		}
	}

}}	// namespace dmc::hue
