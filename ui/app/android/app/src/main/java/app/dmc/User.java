package app.dmc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.dmc.core.Persistence;

/**
 * Created by Joscormir on 02/03/2015.
 */
public class User {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(String _userID){
        assert sInstance == null;
        sInstance = new User(_userID);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static User get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List getHubsIds(){
        return mHubsId;
    }


    //-----------------------------------------------------------------------------------------------------------------
    public boolean setLastHubID(String _lastHubID){
         try {
            mLastHub.put("id", _lastHubID);
            return true;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String getLastHubID(){
        try {
            return mLastHub.getString("id"); //hubID
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void createHub(String _hubId){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //Private interface
    private User(String _userID){
        try {
            JSONArray userJSONHubs = Persistence.get().getJSON(_userID).getJSONArray("hubs");
            for(int i = 0; i < userJSONHubs.length(); ++i){
                mHubsId.add(userJSONHubs.getJSONObject(i).getString("hubId"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static  User             sInstance = null;
    private static JSONObject        mLastHub;
    private static List<String>      mHubsId;
}
