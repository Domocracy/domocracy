////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2015/Feb/11
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_HOME_ROOM_H_
#define _DMCSERVER_HOME_ROOM_H_

#include <string>
#include <unordered_map>

namespace dmc {

	class Device;

	class Room {
	public:
		const std::string&	name() const { return mName; }
		unsigned			id	() const { return mId; }

	private:
		std::string mName;
		unsigned	mId;
		std::unordered_map<unsigned, Device*>	mDevices;
	};
}

#endif // _DMCSERVER_HOME_ROOM_H_