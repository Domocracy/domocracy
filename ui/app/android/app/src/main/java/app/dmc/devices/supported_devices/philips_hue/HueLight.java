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
    public JSONObject action(JSONObject _stateInfo) {
		JSONObject command;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

	private int mHue = 0;
	private int bSat = 0;
	private int mBri = 0;
	private boolean mOn = false;

}
