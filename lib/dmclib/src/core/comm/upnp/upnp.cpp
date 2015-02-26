////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/26
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "upnp.h"
#include <core/comm/socket/socket.h>
#include <string>
#include <iostream>

using namespace std;

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	Upnp::Upnp() {
	}

	//------------------------------------------------------------------------------------------------------------------
	void Upnp::discoverServices() {
		Socket socket;
		const string cMulticastAddress;
		bool ok = socket.connectTo(cMulticastAddress, 1900, Socket::Protocol::UDP);
		if(ok)
			cout << "Connected to multicast Ip\n";
		else
			cout << "Error: Unable to connect to multicast ip\n";
	}
	
}
