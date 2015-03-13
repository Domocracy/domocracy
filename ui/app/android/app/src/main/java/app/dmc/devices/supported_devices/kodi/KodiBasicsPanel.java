package app.dmc.devices.supported_devices.kodi;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;

import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

/**
 * Created by Joscormir on 13/03/2015.
 */
public class KodiBasicsPanel extends DevicePanel {

    public KodiBasicsPanel(final Device _parentActuator, int _layoutResId, Context _context) {
        super(_parentActuator, _layoutResId, _context);

        init(_context);
        setCallbacks();
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void play(){
        setState
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void pause(){}

    //-----------------------------------------------------------------------------------------------------------------
    public void resume(){}

    //-----------------------------------------------------------------------------------------------------------------
    public void stop(){}

    //-----------------------------------------------------------------------------------------------------------------
    // Private interface
    private void init(Context _context){
    }
    //-----------------------------------------------------------------------------------------------------------------
    // View set up methods
    private void setCallbacks(){
        extendCallback();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void extendCallback(){
        // Update tvshowlist
        mTvShowSelector.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((Kodi) device()).loadTvShows();
                return false;
            }
        });
    }

    // Private members
    private Spinner mTvShowSelector;
    private ArrayAdapter<String> mSpinnerAdapter;

    private JSONArray mTvShowList;

}
