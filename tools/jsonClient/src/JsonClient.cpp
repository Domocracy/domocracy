///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy Tools: json Client
//		Author:	Pablo R.S.
//		Date:	2015-JUN-02
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


#include "JsonClient.h"

#include <iostream>

namespace dmc_tools{
	//---------------------------------------------------------------------------------------------------------------------
	JsonClient::JsonClient(std::string _host, unsigned _port){
		// Init connection.
		mSocket.open(_host, _port);

		// If connection initialized, start listening.
		if (mSocket.isOpen()){
			mIsListening = true;
			mListenThread = new std::thread(&JsonClient::listenCallback, this);

		}
	}
	//---------------------------------------------------------------------------------------------------------------------
	JsonClient::~JsonClient(){
		close();
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	int JsonClient::send(cjson::Json _json){
		if (mSocket.isOpen()){
			return mSocket.write(_json.serialize());
		}
		else{
			std::cout << "[ERROR] - Connection is closed" << std::endl;
			return -1;
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	bool JsonClient::isConnected(){
		return mSocket.isOpen();
	}

	//---------------------------------------------------------------------------------------------------------------------
	void JsonClient::listenCallback(){
		const int cMaxLen = 1024;
		char buffer[cMaxLen];

		while (mIsListening){
			int len = mSocket.read(buffer, cMaxLen);
			if (len > 0)
				std::cout << "[RECEIVED]: " << std::string(buffer, len) << std::endl;
			else if(len == -1){
				close();
				std::cout << "[LOG]: Connection closed" << std::endl;
			}
		}

		std::cout << "[LOG]: Stopped listening" << std::endl;
	}

	//---------------------------------------------------------------------------------------------------------------------
	void JsonClient::close(){
		mIsListening = false;
		mSocket.close();
	}
}
