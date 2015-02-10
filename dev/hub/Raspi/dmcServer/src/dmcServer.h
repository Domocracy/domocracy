////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#ifndef _DMCSERVER_DMCSERVER_H_
#define _DMCSERVER_DMCSERVER_H_

#include <string>

namespace dmc {
	
	class DmcServer {
	public:
		DmcServer	(int _argc, const char** _argv);
		~DmcServer();
		// Return false means the application should exit
		bool update	();

	private:
		void processArguments	(int _argc, const char** _argv);

		// Config
		unsigned mHttpPort = 80;
	};

}

#endif // _DMCSERVER_DMCSERVER_H_