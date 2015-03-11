////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_DEVICE_H_
#define _DMCSERVER_HOME_DEVICE_H_

#include <string>
#include <core/comm/json/json.h>

namespace dmc {

	class Device {
	public:
		virtual ~Device() = default;

		unsigned			id			() const { return mId; }
		const std::string	name		() const { return mName; }
		virtual Json		read		(const Json& _request) const { return Json(); }
		virtual Json*		serialize	() const;

	protected:
		Device(unsigned _id, const std::string& _name) : mId(_id), mName(_name) {}

	private:
		unsigned	mId;
		std::string	mName;
	};

	//------------------------------------------------------------------------------------------------------------------
	inline Json* Device::serialize() const {
		Json * devData = new Json("{}");
		(*devData)["id"].setInt((unsigned)mId);
		(*devData)["name"].setText(mName);
		return devData;
	}

}	// namespace dmc

#endif // _DMCSERVER_HOME_DEVICE_H_