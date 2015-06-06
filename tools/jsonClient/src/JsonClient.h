///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy Tools: json Client
//		Author:	Pablo R.S.
//		Date:	2015-JUN-02
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


#ifndef DOMOCRACY_TOOLS_JSON_CLIENT_H_
#define DOMOCRACY_TOOLS_JSON_CLIENT_H_

#include <string>
#include <cjson/json.h>
#include <core/comm/socket/socket.h>

namespace dmc_tools{

	class JsonClient{
	public:		// Public interface
		JsonClient(std::string _host, unsigned _port);
		~JsonClient();

		int send(cjson::Json);

		bool isConnected();
	private:	// Private methods
		void listenCallback();

		void close();
	private:	//	Members
		std::thread *mListenThread;
		bool mIsListening;

		dmc::Socket mSocket;

	};	//	class JsonClient
}	//	namespace dmc_tools

#endif // !DOMOCRACY_TOOLS_JSON_CLIENT_H_
