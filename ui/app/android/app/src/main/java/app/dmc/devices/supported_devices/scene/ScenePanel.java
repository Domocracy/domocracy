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
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.User;
import app.dmc.devices.ActuatorPanel;
import app.dmc.user_interface.PanelList;

public class ScenePanel extends ActuatorPanel {
    public ScenePanel(Scene _parent, JSONObject _panelData, int _layoutResId, Context _context, JSONArray _devicesData) {
        super(_parent, _panelData, _layoutResId, _context);

        mExpandButton = (Button) findViewById(R.id.expandViewButton);
        mExtendedView = (LinearLayout) findViewById(R.id.extendedLayout);

        mDevData = _devicesData;
		mParentScene = _parent;

        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

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
                if(mDeviceList == null){
                    mDeviceList = new PanelList(mDevData, User.get().getCurrentHub(), getContext());
                    mExtendedView.addView(mDeviceList);
                }
				if(!mExpanded)
					onExpandView();
				else
					onCollapseView();
            }
        });
    }

	//-----------------------------------------------------------------------------------------------------------------
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
		mExpanded = true;
		mParentScene.captureState();
		displayExtendedView(); // Display extended view
    }

	//-----------------------------------------------------------------------------------------------------------------
	private void onCollapseView() {
		mExpanded = false;
		mParentScene.saveModifications();
		mParentScene.restoreState();
		hideExtendedView();
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void displayExtendedView() {
		float iniY = -1;
		Animation slideDown = new TranslateAnimation(   Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, iniY,
				Animation.RELATIVE_TO_SELF, 0);
		slideDown.setDuration(400);

		mExtendedView.setAnimation(slideDown);
		mExtendedView.setVisibility(View.VISIBLE);
		mExpandButton.setBackgroundResource(R.drawable.collapse_button_selector);
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void hideExtendedView() {
		mExtendedView.setVisibility(View.GONE);
		mExpandButton.setBackgroundResource(R.drawable.extend_button_selector);
	}

    //-----------------------------------------------------------------------------------------------------------------
    // private members
    private Button          mExpandButton;
    private LinearLayout    mExtendedView;
    private PanelList       mDeviceList;

    private JSONArray       mDevData;
	private JSONArray		mDevCommand;
	private boolean			mExpanded;
	private Scene			mParentScene;
}