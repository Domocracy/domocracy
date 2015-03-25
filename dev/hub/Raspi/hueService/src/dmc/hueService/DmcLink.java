///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Domocracy: Hue Service
//			Author:	Pablo R.S.
//			Date:	2015-MAR-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package dmc.hueService;

import java.io.IOException;
import java.net.ServerSocket;

public class DmcLink {
	public static final int DMC_PORT = 5028;
	
	//-----------------------------------------------------------------------------------------------------------------
	// public interface
	DmcLink(){
		try {
			mServer = new ServerSocket(DMC_PORT);
		} catch (IOException _ioException) {
			_ioException.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	// Private methods
	
	
	//-----------------------------------------------------------------------------------------------------------------
	// Private members
	ServerSocket mServer;
}
