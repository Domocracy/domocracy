////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Ag�era Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_
#define _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_

#include <string>
#include <core/comm/json/json.h>
#include <core/comm/socket/socket.h>
#include <home/device/actuator.h>

namespace dmc { namespace kodi {

	class Kodi final : public Actuator{
	public:
		Kodi(const Json&);

		bool runCommand(const Json& _cmd) override;

	private:
		void sendCommand(const Json&);
		Json readComand();

		Json getPlayers();
		Json getMovies();
		void PlayMovie(const Json& _movie);

		std::string mIp;
		unsigned	mPort = 9090;
		Socket		mTcpConnection;
	};

}}	// namespace dmc::kodi

#endif // _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_