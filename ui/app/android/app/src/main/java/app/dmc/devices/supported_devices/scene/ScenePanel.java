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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;

public class ScenePanel extends ActuatorPanel {
    public ScenePanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context) {
        super(_parentActuator, _panelData, _layoutResId, _context);

        mExpandButton = (Button) findViewById(R.id.expandViewButton);
        mDeviceIdList = new ArrayList<>();

        try{
            JSONArray idList = _panelData.getJSONArray("idlist");
            for(int i = 0; i < idList.length(); i++){
                mDeviceIdList
            }
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
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
                onExpandView();
            }
        });
    }

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
    private void onExpandView(){
        mExtendedView = findViewById(R.id.extendedLayout);

        float iniY = -1;
        Animation slideDown = new TranslateAnimation(   Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, iniY,
                Animation.RELATIVE_TO_SELF, 0);
        slideDown.setDuration(400);

        switch (mExtendedView.getVisibility()){
            case View.VISIBLE:
                mExtendedView.setVisibility(View.GONE);
                mExpandButton.setBackgroundResource(R.drawable.extend_button_selector);
                break;
            case View.GONE:
                mExtendedView.setAnimation(slideDown);
                mExtendedView.setVisibility(View.VISIBLE);
                mExpandButton.setBackgroundResource(R.drawable.collapse_button_selector);
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // private members
    private Button          mExpandButton;
    private View            mExtendedView;

    private List<String>    mDeviceIdList;
}