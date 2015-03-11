///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.devices;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONObject;

public abstract class DevicePanel extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context){
        super(_context);
        mParentDevice = _dev;

        /*setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/
        View.inflate(_context, _layoutResId, this);
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		mIsPaused = false;
		Log.d("DOMOCRACY", "Intercepted Touch event. X: " + ev.getX() + "; Y: " + ev.getY());
		return false;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public void pause(){
		mIsPaused = true;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public final JSONObject action(){
		if(!mIsPaused)
			mCommand = queryDeviceCommand();
		return mCommand;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public JSONObject queryDeviceCommand () {
		return mParentDevice.action(null);
	}

    //-----------------------------------------------------------------------------------------------------------------
    public void destroy(){
        mParentDevice.unregisterPanel(this);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

	//-----------------------------------------------------------------------------------------------------------------
	public Device device() { return mParentDevice; }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract void stateChanged(JSONObject _state);

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    Device mParentDevice;
	protected JSONObject mCommand;
	boolean mIsPaused = false;
}
