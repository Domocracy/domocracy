///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;
import app.dmc.devices.Actuator;

public class HueLight implements Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public HueLight(JSONObject _data, Context _context){
        try {
            mName = _data.getString("name");
            mId = _data.getString("id");
            mHubId = _data.getString("hub");
            mState = new State(true, 255, 0, 0);

        }catch(JSONException _jsonException) {
            _jsonException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            // Get base layout
            LayoutInflater inflater = LayoutInflater.from(_context);
            mView = inflater.inflate(R.layout.hue_light_layout, null);

            // Implement Toggle button
            ToggleButton button = (ToggleButton) mView.findViewById(R.id.toggleButton);
            button.setChecked(mState.on());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mState.on(((ToggleButton) v).isChecked());
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCommand(mState.json());
                        }
                    });
                    t.start();
                }
            });

            // Implement Name
            TextView nameTv = (TextView) mView.findViewById(R.id.devName);
            nameTv.setText(name());

            // Implement intensity bar
            SeekBar intensityBar = (SeekBar) mView.findViewById(R.id.intensityBar);
            intensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int bri = seekBar.getProgress()*255/seekBar.getMax();
                    mState.brightness(bri);

                    mState.on(bri != 0 ? true : false);
                    ((ToggleButton) mView.findViewById(R.id.toggleButton)).setChecked(bri != 0 ? true : false);
                    
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCommand(mState.json());
                        }
                    });
                    t.start();
                }
            });

            // Implement Expandable View
            View shortView = mView.findViewById(R.id.shortLayout);
            shortView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView hueSelector = (ImageView) mView.findViewById(R.id.hueSelector);
                    switch (hueSelector.getVisibility()){
                        case View.VISIBLE:
                            hueSelector.setVisibility(View.GONE);
                            break;
                        case View.GONE:
                            hueSelector.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });

        }
        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void runCommand(final JSONObject _jsonCommand) {
        // Create a new request with own url and using a json.
        Hub hub = HubManager.get().hub(mHubId);
        hub.send("/device/" + id(), _jsonCommand);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String name() {
        return mName;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return  mId;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface

    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String mName;
    private String mId;

    private State mState;

    private String mHubId;
    private View mView;



    //-----------------------------------------------------------------------------------------------------------------
    // State inner class
    private class State{
        public State(JSONObject _data){
            try {
                mIsOn =                 _data.getBoolean("on");
                mBrightness =           _data.getInt("bri");
                mSaturation =           _data.getInt("sat");
                mHue =                  _data.getInt("hue");
            }catch (JSONException _jsonException){
                _jsonException.printStackTrace();
            }
        }

        //-----------------------------------------------------------------------------------------------------------------
        public State(boolean _on, int _brightness, int _saturation, int _hue){
            mIsOn       = _on;
            mBrightness = _brightness;
            mSaturation = _saturation;
            mHue        = _hue;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Setters
        public void on(boolean _on){
            mIsOn = _on;
        }
        public void brightness(int _bri){
            mBrightness = _bri;
        }
        public void saturation(int _sat){
            mSaturation = _sat;
        }
        public void hue(int _hue){
            mHue = _hue;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // getters
        public boolean on(){
            return mIsOn;
        }
        public int brightness(){
            return mBrightness;
        }
        public int saturation(){
            return mSaturation;
        }
        public int hue(){
            return mHue;
        }

        //-----------------------------------------------------------------------------------------------------------------
        public JSONObject json(){
            JSONObject state = new JSONObject();
            try {
                state.put("on",     mIsOn);
                state.put("bri",    mBrightness);
                state.put("sat",    mSaturation);
                state.put("hue",    mHue);
            }catch(JSONException _jsonException){
                _jsonException.printStackTrace();
            }
            return state;
        }

        //-------------------------------------------------------------------------------------------------------------
        private boolean mIsOn;
        private int    mBrightness;
        private int    mSaturation;
        private int     mHue;

    }
}
