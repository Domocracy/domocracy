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
import java.util.ArrayList;
import java.util.List;

public class DmcLink {
	public static final int DMC_PORT = 5028;
	
	//-----------------------------------------------------------------------------------------------------------------
	// public interface
	DmcLink(){
		try {
			mClientList = new ArrayList<>();
			mAcceptSocket = new ServerSocket(DMC_PORT);
			
			mAcceptThread = new Thread(){
				@Override
				public void run(){
					while(mRunning){
						acceptCallback();
					}
				}
			};
			mAcceptThread.start();			
			
		} catch (IOException _ioException) {
			_ioException.printStackTrace();
		}
		
		
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	public void disconnect(){
		mRunning = false;
		for(Delegate delegate:mClientList){
			delegate.close();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	// Private methods
	private void acceptCallback(){
		try {
			Socket socket = mAcceptSocket.accept();
			if(socket.isConnected()){
				mClientList.add(new Delegate(socket));
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	// Private members
	ServerSocket 	mAcceptSocket;
	List<Delegate>	mClientList;
	
	Thread			mAcceptThread;
	boolean			mRunning = false;
	
	//-----------------------------------------------------------------------------------------------------------------
	// Inner classes
	
	class Delegate{
		//-----------------------------------------------------------------------------------------------------------------
		// Private Members 
		Delegate(Socket _socket){
			mController = HueController.get();
			mSocket = _socket;
			try {
				mOutput =  new PrintWriter(mSocket.getOutputStream(), true);
				mInput = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
				

				mListenThread = new Thread(){
					@Override
					public void run(){
						while(mListening){
							listenCallback();
						}
					}
				};
				mListenThread.start();
				
			} catch (IOException e) {
				System.out.println("Failed creating delegate with socket:" + mSocket);
				e.printStackTrace();
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------
		public void close(){
			mListening = false;
			try {
				mInput.close();
				mOutput.close();
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------
		private void listenCallback(){
			try {
				String msg = null;
				if((msg = mInput.readLine()) != null){
					if(msg.equals("ip")){
						List<String> ips = mController.bridgesIps();
						if(ips.size() != 0)
							mOutput.println(ips.get(0));	//666 Only supported 1 bridge 
					}			
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//-----------------------------------------------------------------------------------------------------------------
		// Private Members
		private Socket mSocket;
		private Thread mListenThread;
		private BufferedReader mInput;
	    private PrintWriter mOutput;
	    private HueController mController;
	    private Boolean mListening = true;
	}
}
