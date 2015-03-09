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
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;

public class ScenePanel extends ActuatorPanel {
    public ScenePanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context) {
        super(_parentActuator, _panelData, _layoutResId, _context);

        mExpandButton = (Button) findViewById(R.id.expandViewButton);

        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private void onClickRunCommand(){
        Thread commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Put dev in "Sending mode"
                // Send Response
                try {
                    JSONObject cmdRequest = new JSONObject();
                    cmdRequest.put("method", "PUT");
                    cmdRequest.put("cmd", new JSONObject());
                    JSONObject response = mParentActuator.runCommand(cmdRequest);
                } catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
                // if(response OK){
                //      Dev in mode OK
                //else
                //      Dev back to last state

            }
        });
        commThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setCallbacks(){
        // Generic click callback
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRunCommand();
            }
        });

        mExpandButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Expand View
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    //

    private Button mExpandButton;

}