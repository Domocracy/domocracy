package app.dmc.devices.supported_devices.kodi;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

/**
 * Created by Joscormir on 13/03/2015.
 */
public class KodiBasicsPanel extends DevicePanel {

    public KodiBasicsPanel(final Device _parentActuator, int _layoutResId, Context _context) {
        super(_parentActuator, _layoutResId, _context);

        mToggleButton       = (ToggleButton)    findViewById(R.id.playButton);
        mStopButton         = (Button)          findViewById(R.id.stopButton);
      mIntensityBar         = (SeekBar)         findViewById(R.id.intensityBar);
        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void play(){
        JSONObject request = new JSONObject();
        try{
            request.put(COMMAND,"play");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
           e.printStackTrace();
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    private  void pauseShow(){
        JSONObject request = new JSONObject();
        try{
            request.put(COMMAND,"pause");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void resume(){
        JSONObject request = new JSONObject();
        try{
            request.put(COMMAND,"resume");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void stop(){
        JSONObject request = new JSONObject();
        try{
            request.put(COMMAND,"stop");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onToggleButtonCallback(){
        if(mToggleButton.isChecked())
            pauseShow();
        else
            resume();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onIntensityBarCallback(){
        int volumeLevel = mIntensityBar.getProgress() * 100 / mIntensityBar.getMax();
        JSONObject request = new JSONObject();
            try{
                request.put(COMMAND,"setVolume");
                request.put(VOLUME,volumeLevel);

                ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void extendCallback(){
        // ToggleButton action
        mToggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleButtonCallback();
            }
        });
        //Stop Button
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        //Volume Intensity Bar
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
    }
    //-----------------------------------------------------------------------------------------------------------------
    // View set up methods
    private void setCallbacks(){
        extendCallback();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject serialize(){
        JSONObject serial = new JSONObject();
        try{
            serial.put("type", Kodi.PANEL_TYPE_BASICS);
            serial.put("devId", device().id());
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
            return null;
        }

        return serial;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members

    private ToggleButton    mToggleButton;
    private Button          mStopButton;
    private SeekBar         mIntensityBar;

    final static String COMMAND = "cmd";
    final static String VOLUME  = "volume";
}
