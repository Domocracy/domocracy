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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ToggleButton;

import org.json.JSONObject;

import app.dmc.R;
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
        return new HuePanel(this, _panelData, R.layout.hue_light_layout, _context);
    }

    @Override
    public JSONObject runCommand(JSONObject _jsonCommand) {
        return new JSONObject();
    }

    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }



    //-----------------------------------------------------------------------------------------------------------------
    // Panels
    class HuePanel extends ActuatorPanel{
        HuePanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context){
            super(_parentActuator, _panelData, _layoutResId, _context);

            ToggleButton button = (ToggleButton) findViewById(R.id.toggleButton);

            button.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //Log.d("DOMOCRACY", "Touch event on toggle button. X: " + event.getX() + "; Y: " + event.getY());
                    return false;
                }
            });
        }

        @Override
        public void stateChanged(JSONObject _state) {

        }
    }
}
