package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Hubs
        HubManager hubManager = HubManager.getInstance();
        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...
        mUI = new UserInterface(this, hubManager.hubsIds(),hubManager.defaultHub());

    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;

   }
