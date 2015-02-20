////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "kodi.h"
#include <iostream>

using namespace std;

namespace dmc { namespace kodi {

	//------------------------------------------------------------------------------------------------------------------
	Kodi::Kodi(const Json& _data) 
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		mIp = _data["ip"].asText();

		mTcpConnection.connectTo(mIp, mPort);
		// Try to ping Kodi
		Json request(R"({"jsonrpc": "2.0", "method": "GUI.ShowNotification", 
						"params":{"title":"DMC", "message":"Domocratizate"},"id": 1})");
		string msg;
		request >> msg;
		socket.write(msg);

		// Wait for response from the server
		const unsigned bufferSize = 64*1024;
		char buffer[bufferSize+1];
		int nBytes = socket.read(buffer, bufferSize);
		buffer[nBytes] = '\0';
		std::string dst(buffer);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::runCommand(const Json&) {
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Kodi::sendCommand(const Json& _cmd) {

	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getPlayers() {

	}

}}	// namespace dmc::kodi