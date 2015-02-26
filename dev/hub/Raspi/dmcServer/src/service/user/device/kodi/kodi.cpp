////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/19
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "kodi.h"
#include <iostream>
#include <core/comm/json/rpc/jsonRpc.h>

using namespace std;

namespace dmc { namespace kodi {

	//------------------------------------------------------------------------------------------------------------------
	Kodi::Kodi(const Json& _data) 
		:Actuator(_data["id"].asInt(), _data["name"].asText())
		,Device(_data["id"].asInt(), _data["name"].asText())
	{
		mIp = _data["ip"].asText();

		// mTcpConnection.connectTo(mIp, mPort);
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::runCommand(const Json&) {
		Json tvShows = getTvShows();
		return playLastEpisode(tvShows[0]);
	}

	//------------------------------------------------------------------------------------------------------------------
	void Kodi::sendRequest(const Json& _cmd) {
		mTcpConnection.connectTo(mIp, mPort);
		mTcpConnection.write(_cmd.serialize());
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::readResponse() {
		/// 666 TODO: Support concatenated json responses.
		const unsigned bufferSize = 64*1024;
		char buffer[bufferSize+1];
		int nBytes = mTcpConnection.read(buffer, bufferSize);
		buffer[nBytes] = '\0';
		mTcpConnection.close();
		return Json(string(buffer));
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getPlayers() {
		JsonRpcRequest request("Player.GetActivePlayers", Json("{}"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return Json();
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getMovies() {
		JsonRpcRequest request("VideoLibrary.GetMovies", 
			Json(R"({"properties": ["file"]})"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return response["result"]["movies"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getTvShows() {
		JsonRpcRequest request("VideoLibrary.GetTVShows", 
			Json("{}"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return response["result"]["tvshows"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getEpisodes(const Json& _show)
	{
		Json showId = _show["tvshowid"];
		Json command = Json("{}");
		command["tvshowid"] = showId;
		JsonRpcRequest request("VideoLibrary.GetEpisodes",
				command, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return response["result"]["episodes"];
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::playLastEpisode(const Json& _show) {
		Json episodes = getEpisodes(_show);
		Json params(R"({"item":{}})");
		params["item"] = episodes[0];
		JsonRpcRequest request ("Player.Open", params, mLastReqId++);
		sendRequest(request);
		readResponse();
		/// 666 TODO: Check response
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::scanLibrary() {
		JsonRpcRequest request("VideoLibrary.Scan", Json("{}"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return Json();
	}

	//------------------------------------------------------------------------------------------------------------------
	void Kodi::PlayMovie(const Json& _movie) {
		Json params(R"({"item":{}})");
		params["item"]["movieid"] = _movie["movieid"];
		JsonRpcRequest request ("Player.Open", params, mLastReqId++);
		sendRequest(request);
		readResponse();
	}

}}	// namespace dmc::kodi