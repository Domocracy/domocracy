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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;

public class HueLightPanel extends ActuatorPanel {
    HueLightPanel(final Actuator _parentActuator, JSONObject _panelData, int _layoutResId, final Context _context){
        super(_parentActuator, _panelData, _layoutResId, _context);

        final ToggleButton button          = (ToggleButton)    findViewById(R.id.toggleButton);
        TextView nameView        = (TextView)        findViewById(R.id.devName);
        SeekBar intensityBar   = (SeekBar)         findViewById(R.id.intensityBar);

        // Set dev name
        nameView.setText(_parentActuator.name());

        // ToggleButton action
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject command = new JSONObject();
                try {
                    command.put("on", button.isChecked());
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
                Thread commThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Put dev in "Sending mode"
                        // Send Response
                        JSONObject response = mParentActuator.runCommand(command);
                        // if(response OK){
                        //      Dev in mode OK
                        //else
                        //      Dev back to last state
                    }
                });
                commThread.start();

            }
        });

        // Intensity Bar actuator.
        intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                final JSONObject command = new JSONObject();
                try {
                    if(seekBar.getProgress()== 0) {
                        command.put("on", false);
                    }else {
                        int intensity = seekBar.getProgress() * 255 / seekBar.getMax();
                        command.put("on", true);
                        command.put("bri", intensity);
                    }
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }

                Thread commThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Put dev in "Sending mode"
                        // Send Response
                        JSONObject response = mParentActuator.runCommand(command);
                        // if(response OK){
                        //      Dev in mode OK
                        //else
                        //      Dev back to last state
                    }
                });
                commThread.start();
            }
        });

        // Implementation Expandable View
        View shortView = findViewById(R.id.shortLayout);
        shortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        break;
                    case View.GONE:
                        hueSelector.setAnimation(slideDown);
                        hueSelector.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        // Implementation Hue Selector
        ImageView hueSelector = (ImageView) findViewById(R.id.hueSelector);
        hueSelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

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
                            // Put dev in "Sending mode"
                            // Send Response
                            JSONObject response = mParentActuator.runCommand(command);
                            // if(response OK){
                            //      Dev in mode OK
                            //else
                            //      Dev back to last state
                        }
                    });
                    commThread.start();
                }

                return true;
            }
        });
    }

    @Override
    public void stateChanged(JSONObject _state) {

    }
}