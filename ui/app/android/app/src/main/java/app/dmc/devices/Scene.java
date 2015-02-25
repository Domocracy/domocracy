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
import android.view.View;

import org.json.JSONObject;

import app.dmc.R;

public class Scene extends Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Scene(JSONObject _sceneData) {
        super(_sceneData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, JSONObject _panelData, Context _context) {
        return new ScenePanel(this, _panelData, R.layout.scene_layout, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Panels
    class ScenePanel extends ActuatorPanel {
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
                            JSONObject response = runCommand(new JSONObject());

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
}
