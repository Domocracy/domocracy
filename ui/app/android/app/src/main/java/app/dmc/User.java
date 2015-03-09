package app.dmc;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

/**
 * Created by Joscormir on 02/03/2015.
 */

public class User {
    //-----------------------------------------------------------------------------------------------------------------
    public static void init(String _userID,Context _context){
        assert sInstance == null;
        sInstance = new User(_userID, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static User get(){
        return sInstance;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> getHubIDList(){
        return mHubsId;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Hub getCurrentHub(){
        return mLastHub;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(String _hubId){
       mLastHub = HubManager.get().hub(_hubId);
       UserInterface.get().onSetHub();
       //666 TODO Need to call reload method from UserInterface
    }

    //-----------------------------------------------------------------------------------------------------------------
    //Private interface
    private User(String _userID, Context _context){
        HubManager.init(_context, _userID);
        mHubsId = new ArrayList<String>();
        try {
            mLastHub = HubManager.get().hub(Persistence.get().getJSON(_userID).getString("defaultHub"));
            JSONArray userJSONHubs = Persistence.get().getJSON(_userID).getJSONArray("hubs");
            for(int i = 0; i < userJSONHubs.length(); ++i){
                mHubsId.add(userJSONHubs.getJSONObject(i).getString("hubId"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private static  User                sInstance = null;
    private static  Hub                 mLastHub;
    private static  List<String>        mHubsId;
}
