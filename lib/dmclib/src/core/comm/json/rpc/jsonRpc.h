//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2014-Dec-22
//----------------------------------------------------------------------------------------------------------------------
// json object
#ifndef _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_
#define _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_

#include "jsonRpc.h"

namespace dmc {

	class JsonRpc : public Json {
	public:
		// Internal types
		JsonRpc(const std::string& _method, const Json& _params); // Notification request
		JsonRpc(const std::string& _method, const Json& _params, unsigned _id); // Full request

	};
}

#endif // _DMCLIB_CORE_COMM_JSON_RPC_JSONRPC_H_