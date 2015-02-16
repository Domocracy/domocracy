///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S, Jose Enrique Corchado Miralles
//         Date:    2015-FEB-11
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc;


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.devices.Device;
import app.dmc.devices.DeviceManager;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Hub(Context _context, JSONObject _jsonHub){
        try{
            mId         = _jsonHub.getString("id");
            mName       = _jsonHub.getString("name");
            mIp         = _jsonHub.getString("ip");


            mDevMgr = new DeviceManager(_context, _jsonHub.getJSONArray("devices"));

            //666TODO Rooms not implemented

        }catch(JSONException e){
            e.printStackTrace();
        }

        mConnection = new HubConnection();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Device device(String _id){
        return mDevMgr.device(_id);
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
    public JSONObject send(final String _url, final JSONObject _body){
        String url = "http://" + ip() + "/user/dmc64" + _url;
        return mConnection.send(url, _body);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject get(final String _url){
        return mConnection.get(_url);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean modifyIp(String _ip, JSONObject _jsonHub){
        /*try{
            //

        }catch(JSONException e){
            Log.d("decodeJson", e.getMessage());
        }*/
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Identification
    private String          mName;
    private String          mId;
    private String          mIp;

    // Content
    DeviceManager mDevMgr = null;
    HubConnection mConnection = null;
}
