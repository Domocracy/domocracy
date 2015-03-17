//----------------------------------------------------------------------------------------------------------------------
// Project: Domocracy, dmcLib
// Author: Carmelo J. Fdez-Agüera Tortosa (a.k.a. Technik)
// Date: 2015/Feb/19
//----------------------------------------------------------------------------------------------------------------------
// json object

#include "jsonRpc.h"
#include <sstream>

namespace dmc {

	//------------------------------------------------------------------------------------------------------------------
	JsonRpcNotification::JsonRpcNotification(const std::string& _method, const Json& _params)
		:Json("{}") // Empty dictionary
	{
		Json method;
		method.setText(_method);
		(*this)["jsonrpc"] = Json(R"("2.0")");
		(*this)["method"] = method;
		(*this)["params"] = _params;
	}

	//------------------------------------------------------------------------------------------------------------------
	JsonRpcRequest::JsonRpcRequest(const std::string& _method, const Json& _params, unsigned _id)
		:JsonRpcNotification(_method, _params)
	{
		// std::stringstream ss;
		// ss << "\"" <<_id << "\"";
		(*this)["id"].setInt((int)_id);// = Json(ss.str());
	}
}