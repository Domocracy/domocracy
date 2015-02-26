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
		}
		return Json(R"({"result":"fail", "error":"unknown command")");
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::read(const Json& _request) const {
		string command = _request["cmd"].asText();
		if(command == "tvshows") {
			Json response(R"({})");
			Json shows = getTvShows();
			response["tvshows"] = shows;
			response["result"] = Json("\"ok\"");
			return response;
		}
		return Json(R"({"result":"fail", "error":"unknown request")");
	}

	//------------------------------------------------------------------------------------------------------------------
	void Kodi::sendRequest(const Json& _cmd) const {
		mTcpConnection->connectTo(mIp, mPort);
		mTcpConnection->write(_cmd.serialize());
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::readResponse() const {
		/// 666 TODO: Support concatenated json responses.
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
		Json response = readResponse();
		return Json();
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getMovies() const {
		JsonRpcRequest request("VideoLibrary.GetMovies", 
			Json(R"({"properties": ["file"]})"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return response["result"]["movies"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getTvShows() const {
		JsonRpcRequest request("VideoLibrary.GetTVShows", 
			Json("{}"), mLastReqId++);
		sendRequest(request);
		Json response = readResponse();
		return response["result"]["tvshows"];
	}

	//------------------------------------------------------------------------------------------------------------------
	Json Kodi::getEpisodes(const Json& _showId)
	{
		Json command = Json("{}");
		command["tvshowid"] = _showId;
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
	bool Kodi::PlayMovie(const Json& _movie) {
		Json params(R"({"item":{}})");
		params["item"]["movieid"] = _movie["movieid"];
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

}}	// namespace dmc::kodi