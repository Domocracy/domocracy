////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy - dmcServer
//		Author:	Pablo R.S.
//		Date:	2015-JUN-02
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

#include "JsonServer.h"

#include <iostream>

namespace dmc{
	//--------------------------------------------------------------------------------------------------------------------
	JsonServer::JsonServer(unsigned _port){
		mSocket = new SocketServer(_port);
		if (mSocket == nullptr){
			std::cout << "Unable to start Json server on port: " << _port << std::endl;
		}
		else{
			std::cout << "Json Server is listening on port: " << _port << std::endl;
		}

	}

	//--------------------------------------------------------------------------------------------------------------------
	JsonServer::~JsonServer(){
		delete mSocket;
	}

	//--------------------------------------------------------------------------------------------------------------------
	void JsonServer::onNewConnection(dmc::Socket* _socket){

	}

	//--------------------------------------------------------------------------------------------------------------------
	bool JsonServer::dispatch(){
		return false;	// Not implemented yet.
	}

}	//	namespace dmc.