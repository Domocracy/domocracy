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
import android.util.Pair;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public List<Pair<String,Boolean>> panelTypes(){
        List<Pair<String,Boolean>> types = new ArrayList<>();
        types.add(new Pair<>(PANEL_TYPE_HUE_LIGHT, true));
        return types;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        if(_type.equals(PANEL_TYPE_HUE_LIGHT)) {
            return new HueLightPanel(this, R.layout.hue_light_layout, _context);
        }
        return null;
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

	//-----------------------------------------------------------------------------------------------------------------
	public int bri() {
		return mBri;
	}

	private int mHue = 0;
	private int mSat = 0;
	private int mBri = 0;
	private boolean mOn = false;

    protected static String PANEL_TYPE_HUE_LIGHT = "HueLight";
}
