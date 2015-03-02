//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015/Feb/19
//----------------------------------------------------------------------------------------------------------------------
// json object
#ifndef _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_
#define _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_

#include "../json.h"

namespace dmc {

	class JsonRpcNotification : public Json {
	public:
		// Internal types
		JsonRpcNotification(const std::string& _method, const Json& _params);
	};

	class JsonRpcRequest : public JsonRpcNotification {
	public:
		// Internal types
		JsonRpcRequest(const std::string& _method, const Json& _params, unsigned _id); // Full request
	};
}

#endif // _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_