///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices;

import android.content.Context;

import org.json.JSONObject;

import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;
import app.dmc.devices.DevicePanel;

public class HueLight extends Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public HueLight(JSONObject _devData){
        super(_devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, JSONObject _panelData, Context _context) {
        ActuatorPanel huePanel = new ActuatorPanel(this, _panelData, _context) {
            @Override
            public void stateChanged(JSONObject _state) {

            }

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };

        return huePanel;
    }

    @Override
    public void runCommand(JSONObject _jsonCommand) {

    }

    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }


}
