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
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;
import app.dmc.user_interface.PanelList;

public class ScenePanel extends DevicePanel {

	//-----------------------------------------------------------------------------------------------------------------
    public ScenePanel(Scene _parent, JSONArray _panelsData, JSONArray _childActions, int _layoutResId, Context _context) {
        super(_parent, _layoutResId, _context);

        mExpandButton = (Button) findViewById(R.id.expandViewButton);
        mExtendedView = (LinearLayout) findViewById(R.id.extendedLayout);

		mPanelData = _panelsData;
		mChildActions = _childActions;
		mParentScene = _parent;

        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject serialize(){
        JSONObject serial = new JSONObject();
        try{
            serial.put("type", Scene.PANEL_TYPE_SCENE);
            serial.put("devId", device().id());
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
            return null;
        }

        return serial;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void setCallbacks(){
        // Generic click callback
        mExpandButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
			if(mDeviceList == null){
				mDeviceList = new PanelList(mPanelData, User.get().getCurrentHub(), getContext());
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
    private void onExpandView(){
		mExpanded = true;
		mCapturedState = captureState();
		displayExtendedView(); // Display extended view
		pauseChildren();
    }

	//-----------------------------------------------------------------------------------------------------------------
	private JSONArray captureState() {
		JSONArray state = new JSONArray();
		for( DevicePanel child : mDeviceList.panels() ) {
			state.put(child.action());
		}
		return state;
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void pauseChildren() {
		for( DevicePanel child : mDeviceList.panels() ) {
			child.pause();
		}
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void onCollapseView() {
		mExpanded = false;
		JSONArray finalState = captureState();
		mParentScene.saveModifications(finalState);
		restoreState(mCapturedState);
		hideExtendedView();
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void restoreState(JSONArray _state) {
		for(int i = 0; i < _state.length(); ++i) {
			try {
				JSONObject command = _state.getJSONObject(i);
				Device dev = mDeviceList.panels().get(i).device();
				dev.setState(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

		initChildrenState();
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void initChildrenState() {
		JSONArray childrenActions = mParentScene.childCommnands();
		for(int i = 0; i < childrenActions.length(); ++i) {
			try {
				JSONObject command = childrenActions.getJSONObject(i);
				mDeviceList.panels().get(i).onStateChange(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

    private JSONArray       mPanelData;
	private boolean			mExpanded;
	private Scene			mParentScene;
	private JSONArray		mCapturedState;
	private JSONArray		mChildActions;
}