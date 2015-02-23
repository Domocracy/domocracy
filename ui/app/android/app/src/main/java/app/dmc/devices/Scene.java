///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-19
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.devices;

import android.content.Context;

import org.json.JSONObject;

public class Scene extends Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
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
