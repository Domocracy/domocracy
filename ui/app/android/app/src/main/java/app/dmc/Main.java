package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Init HubManager
        HubManager.init(this);

        //Persistence.init(this);
        //Persistence.getData("hubList");
        try {
            mPrueba = new JSONObject("{\"defaultHub\":\"0\",\"hubs\":[{\"name\":\"Home\",\"id\":\"123\",\"ip\":\"193.147.168.23\",\"rooms\":[],\"devices\":[]},{\"name\":\"Beach Flat\",\"id\":\"543\",\"ip\":\"193.154.123.54\",\"rooms\":[],\"devices\":[]}]}");
        }catch(JSONException e){
            Log.d("Can't initialize Json", e.getMessage());
        }
        Persistence.init(this);
        Persistence.putData(this,"pruebaRara",mPrueba);

        mPruebaRecieved = Persistence.getData(this,"pruebaRara");
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
    private JSONObject mPruebaRecieved = null;
    private JSONObject mPrueba;
    private UserInterface mUI;

   }
