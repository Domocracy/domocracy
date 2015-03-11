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

public abstract class Device {
    //-----------------------------------------------------------------------------------------------------------------
    Device(JSONObject _devData){
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
    public abstract DevicePanel createPanel(String _type, JSONObject _panelData, Context _context);

    //-----------------------------------------------------------------------------------------------------------------
    final public DevicePanel newPanel(String _type, JSONObject _panelData, Context _context){
        DevicePanel panel = createPanel(_type, _panelData, _context);
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
            panel.stateChanged(_state);
        }
    }

	//-----------------------------------------------------------------------------------------------------------------
	protected JSONObject serialize() {
		JSONObject serial = new JSONObject();
		try{
			serial.put("id", mId);
			serial.put("id", mName);
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
