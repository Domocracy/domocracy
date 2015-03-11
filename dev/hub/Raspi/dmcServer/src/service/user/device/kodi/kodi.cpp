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
	Kodi::Kodi(unsigned _id, const Json& _data) 
		:Actuator(_id, _data["name"].asText())
	{
		mIp = _data["ip"].asText();
		mTcpConnection = new Socket();
	}

	//------------------------------------------------------------------------------------------------------------------
	Kodi::~Kodi() {
		delete mTcpConnection;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::runCommand(const Json& _cmd) {
		string command = _cmd["cmd"].asText();
		if(command == "lastEpisode") {
			if(playLastEpisode(_cmd["tvshowid"])) {
				return Json(R"({"result": "ok"})");
			} 
			else 
				return Json(R"({"result":"fail", "error":"unable to play show")");
		}
		else if(command == "movie") {
			if(PlayMovie(_cmd["movieid"])) {
				return Json(R"({"result": "ok"})");
			}
			else 
				return Json(R"({"result":"fail", "error":"unable to play movie")");
		} else if(command == "setVolume") {
			unsigned volume = (unsigned)_cmd["volume"].asInt();
			Json finalVol = setVolume(volume);
			Json result = Json(R"({"result": "ok"})");
			result["volume"] = finalVol;
			return result;
		} else if(command == "pause" || command == "resume") {
			Json result(R"({"result": "ok"})");
			result["state"] = pauseResume();
			return result;
		} else if(command == "stop") {
			stop();
			return Json(R"({"result": "ok"})");
		}
		return Json(R"({"result":"fail", "error":"unknown command")");
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::read(const Json& _request) const {
		string command = _request["cmd"].asText();
		if(command == "tvshows") {
			Json response(R"({})");
			Json shows = getTvShows();
			if(shows.isNill())
				return Json(R"({"result":"fail", "error":"Kodi not available"})");
			else
				response["tvshows"] = shows;
			response["result"] = Json("\"ok\"");
			return response;
		} 
		return Json(R"({"result":"fail", "error":"unknown request")");
	}

	//------------------------------------------------------------------------------------------------------------------
	Json* Kodi::serialize() const {
		Json *base = Actuator::serialize();
		(*base)["ip"].setText(mIp);
		(*base)["type"].setText("Kodi");
		return base;
	}

	//------------------------------------------------------------------------------------------------------------------
	void Kodi::sendRequest(const Json& _cmd) const {
		mTcpConnection->open(mIp, mPort);
		mTcpConnection->write(_cmd.serialize());
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::readResponse() const {
		if(!mTcpConnection->isOpen()) {
			return Json();
		}
		const unsigned bufferSize = 64*1024;
		char buffer[bufferSize+1];
		int nBytes = mTcpConnection->read(buffer, bufferSize);
		buffer[nBytes] = '\0';
		mTcpConnection->close();
		return Json(string(buffer));
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getPlayers() const{
		JsonRpcRequest request("Player.GetActivePlayers", Json("{}"), mLastReqId++);
		sendRequest(request);
		return readResponse();
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getMovies() const {
		JsonRpcRequest request("VideoLibrary.GetMovies", 
			Json(R"({"properties": ["file"]})"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		Json cmdResult = response["result"];
		if(cmdResult["movies"].isNill())
			return Json("[]"); // Empty list
		return cmdResult["movies"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getTvShows() const {
		JsonRpcRequest request("VideoLibrary.GetTVShows", 
			Json("{}"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		Json cmdResult = response["result"];
		if(cmdResult["tvshows"].isNill())
			return Json("[]"); // Empty list
		return cmdResult["tvshows"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getEpisodes(const Json& _showId)
	{
		Json command = Json(R"({"filter": {"field": "playcount", "operator": "is", "value": "0"}, "sort":{"order": "ascending", "method": "dateadded"}})");
		command["tvshowid"] = _showId;
		JsonRpcRequest request("VideoLibrary.GetEpisodes",
				command, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		return response["result"]["episodes"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getPlayer() const {
		Json command = Json("{}");
		JsonRpcRequest request("Player.GetActivePlayers", command, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		return response["result"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::pauseResume() {
		Json player = getPlayer();
		JsonRpcRequest request("Player.PlayPause", player, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		return response["result"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::stop() {
		Json player = getPlayer();
		JsonRpcRequest request("Player.Stop", player, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		return response["result"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::setVolume(unsigned _volume) {
		Json volumeCmd("{}");
		volumeCmd["volume"].setInt((int)_volume);
		JsonRpcRequest request("Application.SetVolume", volumeCmd, mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		if(response.isNill())
			return response;
		return response["result"];
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::playLastEpisode(const Json& _show) {
		Json episodes = getEpisodes(_show);
		if(episodes.isNill()) {
			return true;
		}
		Json params(R"({"item":{}})");
		params["item"]["episodeid"] = episodes[0]["episodeid"];
		JsonRpcRequest request ("Player.Open", params, mLastReqId++);
		sendRequest(request);
		readResponse(); // Prevent return string to stack on read buffer.
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	bool Kodi::PlayMovie(const Json& _movie) {
		Json params(R"({"item":{}})");
		params["item"]["movieid"] = _movie["movieid"];
		JsonRpcRequest request ("Player.Open", params, mLastReqId++);
		sendRequest(request);
		readResponse(); // Prevent return string to stack on read buffer.
		return true;
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::scanLibrary() {
		JsonRpcRequest request("VideoLibrary.Scan", Json("{}"), mLastReqId++);
		sendRequest(request);
		readResponse();
		return Json();
	}

}}	// namespace dmc::kodi