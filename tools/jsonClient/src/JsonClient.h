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
#include <core/comm/socket/socket.h>

namespace dmc_tools{

	class JsonClient{
	public:		// Public interface
		JsonClient(std::string _host, unsigned _port);
		
	private:	// Private methods
		void listenCallback();

	private:	//	Members
		dmc::Socket mSocket;

	};	//	class JsonClient
}	//	namespace dmc_tools

#endif // !DOMOCRACY_TOOLS_JSON_CLIENT_H_
