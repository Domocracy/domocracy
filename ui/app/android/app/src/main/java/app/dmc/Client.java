package app.dmc;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Joscormir on 10/02/2015.
 */

public class Client {
    public static void main(String _argv[]) throws Exception{
        String sentence;
        String modifiedSentence;
        InputStream inFromUser = System.in;

        Socket clientSocket = new Socket("localhost",6789);

        OutputStream outToServer = clientSocket.getOutputStream();
        InputStream inFromServer = clientSocket.getInputStream();


        sentence = inFromUser.toString();
        outToServer.write(sentence.getBytes()); //le paso al server la frase
        modifiedSentence = inFromServer.toString();//recibo del server la frase MODIFICADA
        System.out.println(modifiedSentence);
        clientSocket.close();

    }
}
