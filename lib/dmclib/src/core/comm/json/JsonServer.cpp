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

		mSocket->setConnectionHandler([this](Socket* _connection) {
			onNewConnection(_connection);
		});
	}

	//--------------------------------------------------------------------------------------------------------------------
	JsonServer::~JsonServer(){
		delete mSocket;
	}

	//--------------------------------------------------------------------------------------------------------------------
	void JsonServer::onNewConnection(dmc::Socket* _connection){	// 1 read connection.
		// Read message.
		const unsigned buffLen = 4096;
		char buffer[buffLen];

		unsigned len = _connection->read(buffer, buffLen);
		if (len > 0) {
			// Store message and dispatch it as Json.
			std::string message(buffer, len);

			if (dispatchJson(cjson::Json(message))){
				_connection->write("{\"result\":\"ok\"}");
			}
			else{
				_connection->write("{\"result\":\"Fail\"}");
			}
		}

		// Close connection.
		_connection->close();
		delete _connection;
	}

	//--------------------------------------------------------------------------------------------------------------------
	bool JsonServer::dispatchJson(const cjson::Json &_json){
		std::cout << "Received Json: \n" << _json.serialize() << std::endl;
		return false;	// Not implemented yet.
	}

}	//	namespace dmc.