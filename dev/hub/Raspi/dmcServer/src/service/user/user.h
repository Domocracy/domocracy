////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_USER_USER_H_
#define _DMCSERVER_USER_USER_H_

#include <core/comm/json/json.h>
#include <core/comm/http/httpResponse.h>

namespace dmc {

	namespace http { 
		class Server; 
		class Request;
	}

	class DeviceMgr;

	class User {
	public:
		User(const Json& _userData, http::Server* _serviceToListen, DeviceMgr*);

	private:
		void			processRequest	(http::Server* _s, unsigned _conId, const http::Request& _request);
		std::string		extractCommand	(const std::string& _url) const;
		http::Response*	runCommand		(const std::string& _command, const http::Request& _request) const; // Runs a command and returns the proper http response
		http::Response*	deviceCommand	(const std::string& _devCmd, const http::Request& _request) const;

		std::string mName;
		std::string mId;
		std::string mPrefix;
		DeviceMgr*	mDevices;

		const static std::string	cDeviceLabel;
	};

}	// namespace dmc

#endif // _DMCSERVER_USER_USER_H_