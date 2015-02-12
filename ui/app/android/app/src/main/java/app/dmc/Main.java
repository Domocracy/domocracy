package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
        initJson();
        loadHubs();
        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...
        mUI = new UserInterface(this, mHubMap,mDefaultHub);

    }
    //-----------------------------------------------------------------------------------------------------------------
    public void loadHubs(){
        mHubMap = new HashMap<String,Hub>();

        try {
            JSONArray mHubList = mHubJSON.getJSONArray("hubs");
            mDefaultHub = mHubJSON.getInt("defaultHub");

            for(int i = 0;i < mHubList.length();i++) {
               Hub hub = new Hub(mHubList.getJSONObject(i));
               mHubMap.put(hub.id(), hub);
           }
        }catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }

    public void initJson(){
        try{
            mHubJSON = new JSONObject("{\"defaultHub\":\"0\",\"hubs\":[{\"name\":\"Home\",\"id\":\"123\",\"ip\":\"193.147.168.23\",\"rooms\":[],\"devices\":[]},{\"name\":\"Beach Flat\",\"id\":\"543\",\"ip\":\"193.154.123.54\",\"rooms\":[],\"devices\":[]}]}");
        }catch(JSONException e){
            System.out.println(e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    private UserInterface mUI;
    private Map<String,Hub> mHubMap;
    private JSONObject mHubJSON;
    private int mDefaultHub;

}
