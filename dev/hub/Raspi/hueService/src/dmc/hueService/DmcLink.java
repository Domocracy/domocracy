///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Domocracy: Hue Service
//			Author:	Pablo R.S.
//			Date:	2015-MAR-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package dmc.hueService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DmcLink {
	public static final int DMC_PORT = 5028;
	
	//-----------------------------------------------------------------------------------------------------------------
	// public interface
	DmcLink(){
		try {
			mAcceptSocket = new ServerSocket(DMC_PORT);
			
			System.out.println("Waiting for main app");
			mClientSocket = mAcceptSocket.accept();
		} catch (IOException _ioException) {
			_ioException.printStackTrace();
		}
		
		
	}
	
	public void disconnect(){
		mRunning = false;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	// Private methods
	
	//-----------------------------------------------------------------------------------------------------------------
	// Private members
	ServerSocket 	mAcceptSocket;
	Socket			mClientSocket;
	
	boolean			mRunning = false;
}
