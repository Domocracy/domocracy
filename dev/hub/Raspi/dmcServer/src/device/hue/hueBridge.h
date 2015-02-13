////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/12
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_HUE_HUEBRIDGE_H_
#define _DMCSERVER_HOME_DEVICE_HUE_HUEBRIDGE_H_

#include <core/comm/json/json.h>
#include <string>
#include <thread>
#include <core/comm/http/httpClient.h>

namespace dmc { namespace hue {

	class Bridge {
	public:
		enum class State {
			connected,
			connecting,
			disconnected
		};

		static	Bridge*	load	();

				State	state	() const { return mState; }
				Json	getData	(const std::string& _url);
				bool	putData(const std::string& _url, const Json& _data);

	private:
		Bridge(const Json&);

		std::thread mInitThread;

		void registerUser() {}

		std::string mLocalIp;
		std::string mUsername;
		http::Client* mConn;
		State mState;
	};

}}	// namespace dmc::hue

#endif // _DMCSERVER_HOME_DEVICE_HUE_HUELIGHT_H_