package app.dmc;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Joscormir on 10/02/2015.
 */
public class Server {

    public static void main(String _args[]) throws Exception{
        String clientSentence;
        String changedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            InputStream inFromClient = connectionSocket.getInputStream();
            OutputStream outToClient = connectionSocket.getOutputStream();
            //Here is where we do something
            clientSentence = inFromClient.toString();
            changedSentence = clientSentence.toUpperCase();
            //------------------------------------------------------------
            outToClient.write(changedSentence.getBytes());

        }

    }


}

