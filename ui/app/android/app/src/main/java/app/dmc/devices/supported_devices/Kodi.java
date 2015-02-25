///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices;

import android.content.Context;

import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;
import app.dmc.devices.DevicePanel;

public class Kodi extends Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Kodi(JSONObject _devData){
        super(_devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void runCommand(JSONObject _jsonCommand) {

    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, JSONObject _panelData, Context _context) {
        return new KodiLastShowPanel(this, _panelData, R.layout.kodi_last_show_panel, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Panels
    class KodiLastShowPanel extends ActuatorPanel{
        //-----------------------------------------------------------------------------------------------------------------
        public KodiLastShowPanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context) {
            super(_parentActuator, _panelData, _layoutResId, _context);
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public void stateChanged(JSONObject _state) {

        }
    }
}
