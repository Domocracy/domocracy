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
    public List<String> panelTypes(){
        List<String> types = new ArrayList<>();
        types.add("Complete");
        return types;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        if(_type.equals("Complete")) {
            return new HueLightPanel(this, R.layout.hue_light_layout, _context);
        }
        return null;
    }

    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

}
