////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_USER_USER_H_
#define _DMCSERVER_USER_USER_H_

#include <set>
#include <core/comm/json/json.h>
#include <core/comm/http/httpResponse.h>

namespace dmc {

	namespace http { 
		class Server; 
		class Request;
	}

	class User {
	public:
		User(const Json& _userData, http::Server* _serviceToListen);
		User(unsigned _id, http::Server* _serviceToListen); // New, blank user

		const std::string& strId() const { return mId; }

	private:
		void				processRequest	(http::Server* _s, unsigned _conId, const http::Request& _request);
		std::string			extractCommand	(const std::string& _url) const;
		http::Response		runCommand		(const std::string& _command, const http::Request& _request); // Runs a command and returns the proper http response
		http::Response		addDevice		(const Json& _deviceData);
		http::Response		deviceCommand	(const std::string& _devCmd, const http::Request& _request) const;
		http::Response		reportUserData	() const;
		http::Response		reportDeviceList() const;
		Json				deviceListJson	() const;
		void				loadDevices		(const Json& _deviceList);
		static std::string	idAsString		(unsigned);

		std::string mName;
		std::string mId;
		std::string mPrefix;

		std::set<unsigned>	mDevices;

		const static std::string	cDeviceLabel;
		const static std::string	cAddDevLabel;
	};

}	// namespace dmc

#endif // _DMCSERVER_USER_USER_H_