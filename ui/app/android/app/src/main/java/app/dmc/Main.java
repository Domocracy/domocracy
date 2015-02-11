package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Hubs
        mHubList = new ArrayList<Hub>();
        Hub hub = new Hub();
        mHubList.add(hub);

        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...
        mUI = new UserInterface(this, mHubList,0);

    }


    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;
    private List<Hub> mHubList;
}
