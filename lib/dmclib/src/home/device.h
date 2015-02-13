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

namespace dmc {

	class Device {
	public:
		virtual ~Device() = default;

		unsigned			id		() const { return mId; }
		const std::string	name	() const { return mName; }

	protected:
		Device(unsigned _id, const std::string& _name) : mId(_id), mName(_name) {}

	private:
		unsigned	mId;
		std::string	mName;
	};

}	// namespace dmc

#endif // _DMCSERVER_HOME_DEVICE_H_