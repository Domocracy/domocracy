///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S, Jose Enrique Corchado Miralles
//         Date:    2015-FEB-11
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Hub(JSONObject _jsonHub){
        try{
            mId         = _jsonHub.getString("id");
            mName       = _jsonHub.getString("name");
            mIp         = _jsonHub.getString("ip");
//            mDefaultHub = _jsonHub.getString("defaultHub");
            //666TODO Rooms and devices not implemented

        }catch(JSONException e){
            Log.d("decodeJson", e.getMessage());
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String name(){
        return mName;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String id(){
        return mId;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String ip(){
        return mIp;

    }

    //-----------------------------------------------------------------------------------------------------------------
/*    public String defaultHub(){
        return mDefaultHub;

    }*/
    //-----------------------------------------------------------------------------------------------------------------
    public boolean modifyIp(String _ip, JSONObject _jsonHub){
        try{
            //

        }catch(JSONException e){
            Log.d("decodeJson", e.getMessage());
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject loadJSON(){


    }
    // Identification
    private String          mName;
    private String          mId;
    private String          mIp;

//    private String          mDefaultHub;

    // Content
}
