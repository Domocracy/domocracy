///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.philips_hue;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class HueLightPanel extends DevicePanel {
	//-----------------------------------------------------------------------------------------------------------------
    HueLightPanel(Device _parent, int _layoutResId, final Context _context){
        super(_parent, _layoutResId, _context);

        mToggleButton   = (ToggleButton)    findViewById(R.id.toggleButton);
        mNameView       = (TextView)        findViewById(R.id.devName);
        mIntensityBar   = (SeekBar)         findViewById(R.id.intensityBar);
        mExpandButton   = (Button)          findViewById(R.id.expandViewButton);
        mHueSelector    = (ImageView)       findViewById(R.id.hueSelector);

        mNameView.setText(_parent.name());
        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onStateChange(JSONObject _state) {
		/// 666 TODO
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject serialize(){
        JSONObject serial = new JSONObject();
        try{
            serial.put("type", HueLight.PANEL_TYPE_HUE_LIGHT);
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
        // ToggleButton action
        mToggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleButtonCallback();
            }
        });
        // Intensity Bar actuator.
        mIntensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onIntensityBarCallback();
            }
        });
        // Implementation Expandable View
        mExpandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandView();
            }
        });
        // Implementation Hue Selector
        mHueSelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View _v, MotionEvent _event) {
                return onChangeHue(_v, _event);
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onToggleButtonCallback(){
		JSONObject command = new JSONObject();
		try {
			command.put("on", mToggleButton.isChecked());
		} catch (Exception e) {
			e.printStackTrace();
		}
		device().setState(command);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onIntensityBarCallback(){
        final JSONObject command = new JSONObject();
        try {
            if(mIntensityBar.getProgress()== 0) {
                command.put("on", false);
            }else {
                int intensity = mIntensityBar.getProgress() * 255 / mIntensityBar.getMax();
                command.put("on", true);
                command.put("bri", intensity);
            }
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        Thread commThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject cmdRequest = new JSONObject();
                    cmdRequest.put("method", "PUT");
                    cmdRequest.put("cmd", command);
                    JSONObject response = device().runCommand(cmdRequest);
                } catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
            }
        });
        commThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onExpandView(){
        ImageView hueSelector = (ImageView) findViewById(R.id.hueSelector);

        float iniY = -1;
        Animation slideDown = new TranslateAnimation(   Animation.RELATIVE_TO_SELF, 0,
                                                        Animation.RELATIVE_TO_SELF, 0,
                                                        Animation.RELATIVE_TO_SELF, iniY,
                                                        Animation.RELATIVE_TO_SELF, 0);
        slideDown.setDuration(400);

        switch (hueSelector.getVisibility()){
            case View.VISIBLE:
                hueSelector.setVisibility(View.GONE);
                mExpandButton.setBackgroundResource(R.drawable.extend_button_selector);
                break;
            case View.GONE:
                hueSelector.setAnimation(slideDown);
                hueSelector.setVisibility(View.VISIBLE);
                mExpandButton.setBackgroundResource(R.drawable.collapse_button_selector);
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private boolean onChangeHue(View v, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            float x0 = v.getX();
            float y0 = v.getY();
            int hue = (int) ((x - x0)/ v.getWidth() * 65535);
            int sat = (int) ((1 - (y - y0) / v.getHeight()) * 255);
            final JSONObject command = new JSONObject();
            try {
                command.put("on", true);
                command.put("hue", hue);
                command.put("sat", sat);
            }catch (JSONException _jsonException){
                _jsonException.printStackTrace();
            }
            Thread commThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject cmdRequest = new JSONObject();
                        cmdRequest.put("method", "PUT");
                        cmdRequest.put("cmd", command);
                        device().runCommand(cmdRequest);
                    } catch (JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }
                }
            });
            commThread.start();
        }

        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //
    private Button          mExpandButton;
    private ToggleButton    mToggleButton;
    private SeekBar         mIntensityBar;
    private TextView        mNameView;
    private ImageView       mHueSelector;
}