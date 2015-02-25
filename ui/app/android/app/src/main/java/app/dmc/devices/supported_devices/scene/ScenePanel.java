///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.scene;

import android.content.Context;
import android.view.View;

import org.json.JSONObject;

import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;

public class ScenePanel extends ActuatorPanel {
    public ScenePanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context) {
        super(_parentActuator, _panelData, _layoutResId, _context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread commThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Put dev in "Sending mode"
                        // Send Response
                        JSONObject response = mParentActuator.runCommand(new JSONObject());

                        // if(response OK){
                        //      Dev in mode OK
                        //else
                        //      Dev back to last state

                    }
                });
                commThread.start();
            }
        });

    }

    @Override
    public void stateChanged(JSONObject _state) {

    }
}