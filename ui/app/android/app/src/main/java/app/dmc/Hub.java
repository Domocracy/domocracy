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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.dmc.core.Persistence;
import app.dmc.devices.Device;
import app.dmc.devices.DeviceManager;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Hub(Context _context, JSONObject _jsonHub) {
        try {
            mId = _jsonHub.getString("id");
            mName = _jsonHub.getString("name");
            mIp = _jsonHub.getString("ip");
            mHubFileName = "hub_" + mId;
            mJSONdefault = new JSONObject("{\"name\": \"Home\",\"id\": \"123\",\"ip\": \"193.147.168.23\"}");
            Persistence.get().putData(mHubFileName, mJSONdefault);
            mDevMgr = new DeviceManager(_context, _jsonHub.getJSONArray("devices"));

            //666TODO Rooms not implemented

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mConnection = new HubConnection();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Device device(String _id) {
        return mDevMgr.device(_id);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String name() {
        return mName;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String id() {
        return mId;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public String ip() {
        return mIp;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject send(final String _url, final JSONObject _body) {
        String url = "http://" + ip() + "/user/dmc64" + _url;
        return mConnection.send(url, _body);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public boolean modifyIp(String _ip) {
        if (!_ip.equals(mIp)) {
            mIp = _ip;
            JSONObject json = Persistence.get().getData(mHubFileName);
            try {
                json.put("ip", _ip);
                Persistence.get().putData(mHubFileName, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        } else return false;
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Identification
    private String          mName;
    private String          mId;
    private String          mIp;
    private String          mHubFileName;
    private JSONObject      mJSONdefault;
    private DeviceManager   mDevMgr;
    HubConnection           mConnection = null;
}