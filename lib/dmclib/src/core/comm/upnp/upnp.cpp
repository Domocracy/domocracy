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
		const string cMulticastAddress = "239.255.255.250";
		bool ok = socket.connectTo(cMulticastAddress, 1900, Socket::Protocol::UDP);
		if(ok)
			cout << "Connected to multicast Ip\n";
		else
			cout << "Error: Unable to connect to multicast ip\n";

		string request = string() + 
			+"M-SEARCH * HTTP/1.1\r\n"
			+"HOST: 239.255.255.250:1900\r\n"
			+"MAN: ssdp:discover\r\n"
			+"MX: 10\r\n"
			+"ST: ssdp:all\r\n\r\n";
		cout << request << "\nSending request\n";

		socket.write(request)

		// socket.write(request);
		// unsigned nBytes = socket.read()
	}
}
