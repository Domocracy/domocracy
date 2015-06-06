///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy Tools: json Client
//		Author:	Pablo R.S.
//		Date:	2015-JUN-02
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


//	Tool to test json dmc server.
//	
//	Arguments:
//		1. Host's url.
//		2. Server's port.
//

#include "JsonClient.h"

#include <iostream>

int main(int _argc, char ** _argv){
	// Check input arguments.
	if (_argc < 2){
		std::cout << "Not enough input arguments" << std::endl;
		return -1;
	}

	// Decode args.
	std::string urlHost(_argv[1]);
	unsigned	port = atoi(_argv[2]);

	// Init Client.
	dmc_tools::JsonClient client(urlHost, port);

	// Main loop.
	std::string stream;
	do{
		std::cin >> stream;
		client.send(cjson::Json(stream));
	} while (strcmp(stream.c_str(), "EXIT") != 0 && client.isConnected());


}