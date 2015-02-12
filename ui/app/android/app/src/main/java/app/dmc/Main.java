package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.devices.DeviceManager;
import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Hubs
        mHubList = new ArrayList<Hub>();
        Hub hub1 = new Hub("City Home", "1");
        Hub hub2 = new Hub("Beach Flat", "2");
        mHubList.add(hub1);
        mHubList.add(hub2);


        // Init Device manager.
        loadDevices();

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
    // Private interface.
    
    private void loadDevices(){
        JSONObject devData = null;
        DeviceManager.init(this, devData);

    }

    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;
    private List<Hub> mHubList;
    private DeviceManager mDevMgr;
}
