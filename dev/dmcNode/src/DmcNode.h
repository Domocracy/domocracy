////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Domocracy - dmcNode
//		Author:	Pablo R.S.
//		Date:	2015-JUN-0
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#ifndef _DMCNODE_DMCNODE_H_
#define _DMCNODE_DMCNODE_H_

#include <core/comm/json/JsonServer.h>

namespace dmc{

	const unsigned cJsonPort = 5028;

	class DmcNode{
	public:		// Interface
		DmcNode();
		~DmcNode();

	private:	// Private methods
		void scan();	// Scan network using upnp protocol

	private:	// Members
		JsonServer *mSocket;

	};
}	//	namespace dmc

#endif	//	 _DMCNODE_DMCNODE_H_