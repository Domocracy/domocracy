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

using namespace std;

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	Upnp::Upnp() {
	}

	//------------------------------------------------------------------------------------------------------------------
	void Upnp::discoverServices() {
		Socket socket;
		const string cMulticastAddress;
		socket.connectTo(cMulticastAddress, 1900, Socket::Protocol::UDP);
	}
	
}
