package app.dmc.devices.supported_devices.kodi;

import android.content.Context;
import android.view.View;
import android.widget.Button;
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

        setCallbacks();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void play(){
        JSONObject request = new JSONObject();
        try{
            request.put("cmd","play");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
           e.printStackTrace();
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void pauseShow(){
        JSONObject request = new JSONObject();
        try{
            request.put("cmd","pause");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void resume(){
        JSONObject request = new JSONObject();
        try{
            request.put("cmd","resume");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void stop(){
        JSONObject request = new JSONObject();
        try{
            request.put("cmd","stop");
            ((Kodi)device()).setState(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // View set up methods
    private void setCallbacks(){
        extendCallback();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void onToggleButtonCallback(){
        if(mToggleButton.isChecked())
            play();
        else
            pauseShow();
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

    // Private members

    private ToggleButton    mToggleButton;
    private Button          mStopButton;

}
