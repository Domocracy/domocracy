package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Hubs
        loadHubs();
        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...
        //mUI = new UserInterface(this, mHubMap,0);

    }
    //-----------------------------------------------------------------------------------------------------------------
    public void loadHubs(){
        mHubMap = new HashMap<String,Hub>();

        try {
            JSONArray mHubList = mHubJSON.getJSONArray("hubs");
            mDefaultHub = mHubJSON.getString("defaultHub");

            for(int i = 0;i < mHubList.length();i++) {
               Hub hub = new Hub(mHubList.getJSONObject(i));
               mHubMap.put(hub.id(), hub);
           }
        }catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }


    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;
    private Map<String,Hub> mHubMap;
    private JSONObject mHubJSON;
    private String mDefaultHub;

}
