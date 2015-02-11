package app.dmc;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Joscormir on 10/02/2015.
 */
public class HubConnection {

    //First method that connect with Client

    private static void connectClient(String _ip, int _port){
       try {
           Socket clientSocket = new Socket(_ip, _port);
           if(clientSocket.isConnected()) {
               InputStream inFromServer = clientSocket.getInputStream();
           }

       }catch(UnknownHostException uhe){
          System.out.println(uhe.getMessage());
       }catch(IOException ioe){
           System.out.println(ioe.getMessage());
       }
    }
    //---------------------------------------------------------------------------------------------

}
