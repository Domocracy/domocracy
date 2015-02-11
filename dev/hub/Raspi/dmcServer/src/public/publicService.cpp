////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "publicService.h"

#include <core/comm/http/response/jsonResponse.h>
#include <core/comm/http/response/response200.h>
#include <core/comm/http/response/response404.h>

using namespace dmc::http;

namespace dmc {
	//------------------------------------------------------------------------------------------------------------------
	void PublicService::registerServices(http::Server* _server) {
		_server->setResponder("/public/ping", ping());
		_server->setResponder("/public/hubInfo", hubInfo());
		_server->setResponder("/public/createUser", createUser());
	}

	//------------------------------------------------------------------------------------------------------------------
	http::Server::UrlHandler PublicService::ping() const {
		return [](Server* _server, unsigned _conId, const Request& _request) {
			_server->respond(_conId, Response200());
		};
	}

	//------------------------------------------------------------------------------------------------------------------
	http::Server::UrlHandler PublicService::hubInfo() const {
		return [](Server* _server, unsigned _conId, const Request& _request) {
			_server->respond(_conId, JsonResponse(Json("{name : \"dmcHub\"}")));
		};
	}

	//------------------------------------------------------------------------------------------------------------------
	http::Server::UrlHandler PublicService::createUser() const {
		return [](Server* _server, unsigned _conId, const Request& _request) {
			_server->respond(_conId, Response404());
		};
	}
}	// namespace dmc