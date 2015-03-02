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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.comm.HubConnection;
import app.dmc.core.Persistence;
import app.dmc.devices.Device;
import app.dmc.devices.DeviceManager;


public class Hub {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public Hub() {
        mConnection = new HubConnection();
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void init(Context _context, JSONObject _jsonHub){
        try {
            mId = _jsonHub.getString("hubId");
            mName = _jsonHub.getString("name");
            mIp = _jsonHub.getString("ip");
            mHubFileName = "hub_" + mId;
            // 666 TODO: no default hub data
            mJSONdefault = new JSONObject("{\"name\": \"Home\",\"id\": \"123\",\"ip\": \"193.147.168.23\"}");
            Persistence.get().putJSON(mHubFileName, mJSONdefault);
            mRoomList = new ArrayList<>();
            JSONArray rooms = _jsonHub.getJSONArray("rooms");

            mDevMgr = new DeviceManager(_jsonHub.getJSONArray("devices"));

            for(int i = 0; i < rooms.length() ; i++){
                mRoomList.add( new Room(rooms.getJSONObject(i), this, _context));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Device device(String _id) {
        return mDevMgr.device(_id);
    }

    //-----------------------------------------------------------------------------------------------------------------

    public List<Room> rooms(){
        return mRoomList;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Room room(String _id){
        for(int i = 0 ; i < mRoomList.size() ; i++) {
            if(mRoomList.get(i).id().equals(_id))
                return mRoomList.get(i);
        }
        return null;
    }


    //-----------------------------------------------------------------------------------------------------------------
    public String name(){
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
    public JSONObject get(final String _url) {
        String url = "http://" + ip() + "/user/dmc64" + _url;
        return mConnection.get(url);
    }

    //-----------------------------------------------------------------------------------------------------------------


    public boolean modifyIp(String _ip) {
        if (!_ip.equals(mIp)) {
            mIp = _ip;
            JSONObject json = Persistence.get().getJSON(mHubFileName);
            try {
                json.put("ip", _ip);
                Persistence.get().putJSON(mHubFileName, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        } else return false;
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Identification
    private String          mIp;
    private String          mId;
    private String          mName;
    private String          mHubFileName;
    private JSONObject      mJSONdefault;
    List<Room> mRoomList;
    DeviceManager   mDevMgr = null;
    HubConnection   mConnection = null;
}
