package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //      else
        //      Init user interface
        mUI = new UserInterface(this);

        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                Hub hubTest = new Hub();
                HubConnection hubCon = new HubConnection(hubTest);
            }
        });
        t.start();

    }


    //-----------------------------------------------------------------------------------------------------------------
    UserInterface mUI;
}
