package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.dmc.devices.DeviceManager;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Hubs
        HubManager hubManager = HubManager.getInstance();

        // Init Device manager.
        loadDevices();

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
    // Private interface.
    
    private void loadDevices(){
        JSONObject devData = null;
        try {
            // Open JSON file
            InputStream is = getAssets().open("Devices.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String raw = new String(buffer, "UTF-8");
            // Decode From raw.
            devData = new JSONObject(raw);
        }catch (IOException _ioException){
            _ioException.printStackTrace();
        } catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        if(devData == null){
            Log.d("Domocracy", "Could not load device list file" );
            return;
        }

        DeviceManager.init(this, devData);

    }

    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;

   }
