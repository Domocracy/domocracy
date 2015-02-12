package app.dmc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Joscormir on 10/02/2015.
 */
public class HubConnection{

    private Socket clientSocket;
    //---------------------------------------------------------------------------------------------

    HubConnection(Hub _hub){
        if (!_hub.lastIP().isEmpty()) {
            connectClient(_hub.lastIP(),80);
            closeClientSocket();
        } else {
            //TODO666 networkserviceDiscovery here (Avahi)
        }
    }
    //---------------------------------------------------------------------------------------------

    private void connectClient(String _ip, int _port) {
        try {
            clientSocket = new Socket(_ip,_port);
            if(clientSocket.isConnected()) {
                System.out.println("Connected");
            }
       }catch(UnknownHostException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
       }catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
       }


    }
    //---------------------------------------------------------------------------------------------
    private void closeClientSocket(){
        if (clientSocket.isConnected()){
            try {
                clientSocket.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
