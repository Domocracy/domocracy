///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import app.dmc.Hub;
import app.dmc.HubManager;

public abstract class Device {
    //-----------------------------------------------------------------------------------------------------------------
    public Device(JSONObject _devData){
        mRegisteredPanels = new HashSet<>();

        try{
            mId     = _devData.getString("id");
            mName   = _devData.getString("name");
            mHubId  = _devData.getString("hub");

        }catch (JSONException _exception){
            _exception.printStackTrace();
        }
    }

    public String name(){ return mName; };
    public String id()  { return mId; };
    public String hub() {return mHubId;};

	//-----------------------------------------------------------------------------------------------------------------
	final public JSONObject runCommand(final JSONObject _request) {
		if(_request == null)
			return null;
		Hub hub = HubManager.get().hub(hub());
		try{
			String method = _request.getString("method");
			if(method.equals("GET"))
				return hub.get("/device/" + id() + "/" + _request.getString("urlget"));
			if(method.equals("PUT"))
				return hub.send("/device/" + id(), _request.getJSONObject("cmd"));

		}catch (JSONException _jsonException){
			_jsonException.printStackTrace();
		}
		return null;
	}

	//-----------------------------------------------------------------------------------------------------------------
	final public void setState(JSONObject _state) {
		if(_state == null)
			return;
		JSONObject command = new JSONObject();
		try {
			command.put("method", "PUT");
			command.put("cmd", _state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		runCommand(_state);
	}

	//-----------------------------------------------------------------------------------------------------------------
	public JSONObject state() {
		return new JSONObject();
	}

	//-----------------------------------------------------------------------------------------------------------------
	public void onStateChange(JSONObject _state) {
		//
	}

    //-----------------------------------------------------------------------------------------------------------------
    public abstract DevicePanel createPanel(String _type, Context _context);

    //-----------------------------------------------------------------------------------------------------------------
    final public DevicePanel newPanel(String _type, Context _context){
        DevicePanel panel = createPanel(_type, _context);
        mRegisteredPanels.add(panel);
        return panel;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void unregisterPanel(DevicePanel _panel){
        if(mRegisteredPanels.contains(_panel))
            mRegisteredPanels.remove(_panel);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void onUpdateState(JSONObject _state){
        // Intentionally blank.
    }

    //-----------------------------------------------------------------------------------------------------------------
    final public void updateState(JSONObject _state) {
        onUpdateState(_state);

        for(DevicePanel panel : mRegisteredPanels){
            panel.onStateChange(_state);
        }
    }

	//-----------------------------------------------------------------------------------------------------------------
	protected JSONObject serialize() {
		JSONObject serial = new JSONObject();
		try{
			serial.put("id", mId);
			serial.put("name", mName);
			serial.put("hub", mHubId);

		}catch (JSONException _exception){
			_exception.printStackTrace();
		}
		return serial;
	}

    //-----------------------------------------------------------------------------------------------------------------
    private String mName;
    private String mId;
    private String mHubId;

    private Set<DevicePanel> mRegisteredPanels;
}
