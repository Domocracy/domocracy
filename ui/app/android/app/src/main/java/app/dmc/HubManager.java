package app.dmc;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dmc.core.Persistence;


/**
 * Created by Joscormir on 13/02/2015.
 */

public class HubManager {

    //-----------------------------------------------------------------------------------------------------------------
    static public void init(Context _context){
        sInstance =  new HubManager();
        sInstance.loadHubs(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static HubManager get(){
        return sInstance;
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
    // Private Interface.
    private HubManager(){ }

    //-----------------------------------------------------------------------------------------------------------------
    private void loadHubs(Context _context){

        JSONObject mHubJSON =  Persistence.get().getJSON("hubList");

        mHubMap  = new HashMap<String,Hub>();
        mHubsIds = new ArrayList<String>();
        try {
            JSONArray mHubList = mHubJSON.getJSONArray("hubs");
            mDefaultHub = mHubJSON.getString("defaultHub");

            for(int i = 0;i < mHubList.length();i++) {

                Hub hub = new Hub();
                hub.init(_context, mHubList.getJSONObject(i));

                mHubMap.put(mHubList.getJSONObject(i).getString("hubId"), hub);
                mHubsIds.add(mHubList.getJSONObject(i).getString("hubId"));

            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void finalize() throws Throwable {
        for(String hubMapIterator : mHubMap.keySet()){
            mHubMap.get(hubMapIterator).finalize();
        }
        super.finalize();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String,Hub> mHubMap;
    private List<String>    mHubsIds;
    private String          mDefaultHub;


    //-----------------------------------------------------------------------------------------------------------------
    private static HubManager sInstance = null;

}
