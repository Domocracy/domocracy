////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "kodi.h"
#include <core/comm/socket/socket.h>
#include <iostream>

using namespace std;

namespace dmc { namespace kodi {

	//------------------------------------------------------------------------------------------------------------------
	Kodi::Kodi(const Json& _data) 
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		mPort = (unsigned)_data["port"].asInt();
		mIp = _data["ip"].asText();

		// Try to ping Kodi
		Socket socket;
		socket.connectTo("localhost", 9090);
		Json request(R"({"jsonrpc": "2.0", "method": "Player.GetActivePlayers", "id": 1})");
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

}}	// namespace dmc::kodi