package app.dmc;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Joscormir on 13/02/2015.
 */
public class HubManager {

    private static final HubManager instance = new HubManager();
    //-----------------------------------------------------------------------------------------------------------------
    private HubManager(){
        //here goes the loadHub
        initJson();
        mHubMap  = new HashMap<String,Hub>();
        mHubsIds = new ArrayList<String>();
        try {
            JSONArray mHubList = mHubJSON.getJSONArray("hubs");
            mDefaultHub = mHubJSON.getString("defaultHub");

            for(int i = 0;i < mHubList.length();i++) {
                Hub hub = new Hub(mHubList.getJSONObject(i));
                mHubMap.put(hub.id(), hub);
                mHubsIds.add(hub.id());
            }
        }catch(JSONException e){
            Log.d("loadHub", e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------

    public static HubManager get(){

        return instance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> hubsIds(){
        return  mHubsIds;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String defaultHub(){
        return mDefaultHub;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Hub hub(String _id){
        Hub hub = mHubMap.get(_id);
        return hub;
    }

    //-----------------------------------------------------------------------------------------------------------------

    public void initJson(){
        try{

            mHubJSON = new JSONObject("{\"defaultHub\":\"0\",\"hubs\":[{\"name\":\"Home\",\"id\":\"123\",\"ip\":\"193.147.168.23\",\"rooms\":[],\"devices\":[]},{\"name\":\"Beach Flat\",\"id\":\"543\",\"ip\":\"193.154.123.54\",\"rooms\":[],\"devices\":[]}]}");
        }catch(JSONException e){
            Log.d("initJson", e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String,Hub> mHubMap;
    private List<String>    mHubsIds;
    private String          mDefaultHub;

    private JSONObject      mHubJSON;


}
