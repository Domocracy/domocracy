//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2014-Dec-21
//----------------------------------------------------------------------------------------------------------------------
// Win32 specific code for sockets
#ifdef _WIN32

#define WIN32_LEAN_AND_MEAN

#include <Windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>

#include "socketWin32.h"

#include <iostream>

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	// Static data definitions
	//------------------------------------------------------------------------------------------------------------------
	bool SocketWin32::sWinSocketReady = false;

	//------------------------------------------------------------------------------------------------------------------
	SocketWin32::SocketWin32() {
		if(!sWinSocketReady) {
			WSADATA wsData;
			int res = WSAStartup(MAKEWORD(2, 2), &wsData);
			if(res == 0) // Success
				sWinSocketReady = true;
			else
				std::cout << "Error: Couldn't init winSocket library. Returned error " << res << "\n";
		}
	}

}	// namespace dmc

#endif // _WIN32