////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_PUBLIC_PUBLICSERVICE_H_
#define _DMCSERVER_PUBLIC_PUBLICSERVICE_H_

#include <core/comm/http/httpServer.h>

namespace dmc {
	class PublicService {
	public:
		// 666 TODO: PublicService(Hub*);
		PublicService(http::Server* server);
		// Services
		http::Server::UrlHandler	ping		() const;
		http::Server::UrlHandler	hubInfo		() const;
		http::Server::UrlHandler	createUser	() const;
	};
}	// namespace dmc

#endif // _DMCSERVER_PUBLIC_PUBLICSERVICE_H_