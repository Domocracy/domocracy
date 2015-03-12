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

    public Hub(String _id, Context _context) {
        mConnection = new HubConnection();
		mId = _id;
		JSONObject hubData = Persistence.get().getJSON("hub_"+_id);

		try {
			mName = hubData.getString("name");
			mIp = hubData.getString("ip");
			mDevices = hubData.getJSONArray("devices");
			mRooms = hubData.getJSONArray("rooms");

			mDevMgr = new DeviceManager(hubData.getJSONArray("devices"));
			mRoomList = new ArrayList<>();
			for(int i = 0; i < mRooms.length() ; i++){
				mRoomList.add( new Room(mRooms.getJSONObject(i), this, _context));
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
            return true;
        } else return false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void save(){
        JSONObject jsonToSave = new JSONObject();
        try {
            jsonToSave.put("hubId", mId);
            jsonToSave.put("name",mName);
            jsonToSave.put("devices",mDevMgr.serializeDevices());
            jsonToSave.put("rooms",mRooms);
            jsonToSave.put("ip", mIp);
        }catch(JSONException e){
            e.printStackTrace();
        }
        Persistence.get().putJSON("hub_"+mId,jsonToSave);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String          mIp;
    private String          mId;
    private String          mName;
    private JSONArray       mDevices;
    private JSONArray       mRooms;

    List<Room> mRoomList;
    DeviceManager   mDevMgr = null;
    HubConnection   mConnection = null;
}
