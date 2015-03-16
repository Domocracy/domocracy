////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
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
		Kodi(unsigned _id, const Json&);
		~Kodi();

		Json	runCommand	(const Json& _cmd) override;
		Json	read		(const Json& _request) const override;
		Json*	serialize	() const override;

	private:
		void sendRequest	(const Json&) const;
		Json readResponse	() const;

		bool isIdValid		(const Json&) const;

		Json getPlayers		() const;
		Json getMovies		() const;
		Json getTvShows		() const;
		Json getEpisodes	(const Json& _show);
		Json getPlayer		() const;
		Json pauseResume	();
		Json stop			();
		Json setVolume		(unsigned _volume); // 0 to 100
		bool playLastEpisode(const Json& _show);
		bool PlayMovie		(const Json& _movie);
		Json scanLibrary	();

		std::string mIp;
		unsigned	mPort = 9090;
		Socket*		mTcpConnection;

		mutable unsigned	mLastReqId = 1;
	};

}}	// namespace dmc::kodi

#endif // _DMCSERVER_SERVICE_USER_DEVICE_KODI_KODI_H_