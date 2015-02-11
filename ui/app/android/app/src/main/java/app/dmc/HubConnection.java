package app.dmc;
import java.net.Socket;

/**
 * Created by Joscormir on 10/02/2015.
 */
public class HubConnection {

    public static void connectClient(String _ip, int _port) throws Exception{
        try {
            Socket clientSocket = new Socket(_ip, _port);
        }catch(){


        }

    }

}
