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
import android.view.View;

import org.json.JSONObject;

import app.dmc.Hub;
import app.dmc.HubManager;
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
    public void runCommand(final JSONObject _jsonCommand) {
        final Hub hub = HubManager.get().hub(hub());
        Thread commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                hub.send("/device/" + id(), _jsonCommand);
            }
        });
        commThread.start();
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

            // Set click action to the panel.
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject playInfo = new JSONObject();
                    runCommand(playInfo);
                }
            });

        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public void stateChanged(JSONObject _state) {

        }
    }
}
