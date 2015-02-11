////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Project: DMC Server
// Date:	2014/Aug/22
// Author:	Carmelo J. Fdez-Agüera Tortosa
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#include "dmcServer.h"

using namespace dmc;

//----------------------------------------------------------------------------------------------------------------------
int main(int _argc, const char** _argv)
{
	// Create application
	DmcServer mainApplication(_argc, _argv);

	// Run service as long as posible
	while (mainApplication.update())
	{
		// Intentionally blank
	}

	return 0;
}