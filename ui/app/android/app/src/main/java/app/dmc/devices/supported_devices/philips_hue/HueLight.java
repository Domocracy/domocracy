///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.philips_hue;

import android.content.Context;

import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class HueLight extends Device {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public HueLight(JSONObject _devData){
		super(_devData);
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public JSONObject serialize() {
		JSONObject base = super.serialize();
		try {
			base.put("type", "HueLight");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base;
	}

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        return new HueLightPanel(this, R.layout.hue_light_layout, _context);
    }

	//-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject state() {
		JSONObject state = new JSONObject();
		try {
			state.put("bri", mBri);
			state.put("hue", mHue);
			state.put("sat", mSat);
			state.put("on",mOn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public void onStateChange(JSONObject _state) {
		try {
			if(_state.has("on")) {
				mOn = _state.getBoolean("on");
			}
			if(_state.has("bri")) {
				mBri = _state.getInt("bri");
				mOn = true;
			}
			if(_state.has("hue")) {
				mBri = _state.getInt("hue");
				mOn = true;
			}
			if(_state.has("sat")) {
				mSat = _state.getInt("sat");
				mOn = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int mHue = 0;
	private int mSat = 0;
	private int mBri = 0;
	private boolean mOn = false;

}
