package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Init HubManager
        Persistence.init(this);
        HubManager.init(this);

        HubManager.get().hub("123").modifyIp("10.100.5.7");

        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...


        mUI = new UserInterface(this);

    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    // Private interface.
    private UserInterface mUI;

   }
