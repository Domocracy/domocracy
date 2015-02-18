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
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.dmc.devices.DeviceManager;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Hub(Context _context, JSONObject _jsonHub){
        try{
            mId         = _jsonHub.getString("id");
            mName       = _jsonHub.getString("name");
            mIp         = _jsonHub.getString("ip");


            mDevMgr = new DeviceManager(_context, _jsonHub.getJSONObject("devices"));

            //666TODO Rooms not implemented

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

    //-----------------------------------------------------------------------------------------------------------------
   /* public boolean modifyIp(String _ip, JSONObject _jsonHub){


            if(!_ip.equals(mIp)){
                mIp = _ip;
                try{
                    _jsonHub.put("ip",_ip);
                    FileWriter jsonFile = new FileWriter("hubList.json");
                    jsonFile.write(_jsonHub.toString());
                    jsonFile.flush();
                    jsonFile.close();
                }catch(JSONException e){
                    Log.d("modifyJson", e.getMessage());
                }catch(IOException e){
                    Log.d("modifyJsonIO", e.getMessage());
                }
                return true;
            }else return false;
    }*/

    //-----------------------------------------------------------------------------------------------------------------

    // Identification
    private String          mName;
    private String          mId;
    private String          mIp;

    // Content
    DeviceManager mDevMgr = null;
}
