////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy - dmcServer
//		Author:	Pablo R.S.
//		Date:	2015-JUN-02
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


#ifndef _DMCLIB_CORE_COMM_JSON_JSON_SERVER_H_
#define _DMCLIB_CORE_COMM_JSON_JSON_SERVER_H_

#include "../socket/socketServer.h"
#include <cjson/json.h>

#include <unordered_map>

namespace dmc{
	class JsonServer{
	public:		//	 Public Interface
		JsonServer(unsigned _port);
		~JsonServer();

	private:	//	 Private methods
		void onNewConnection(Socket* _socket);
		bool dispatchJson(const cjson::Json &_json);

	private:	//	Members
		SocketServer*	mSocket;
	};

}	//	namespace dmc



#endif	//	_DMCLIB_CORE_COMM_JSON_JSON_SERVER_H_