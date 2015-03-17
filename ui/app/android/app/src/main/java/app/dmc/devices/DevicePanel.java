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
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONObject;

public abstract class DevicePanel extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context){
        super(_context);
        mParentDevice = _dev;
        View.inflate(_context, _layoutResId, this);

        setCallbacks();
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		mIsPaused = false;
		return false;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public void pause(){
		mIsPaused = true;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public final JSONObject action(){
		if(!mIsPaused)
			mCommand = queryAction();
		return mCommand;
	}

	//-----------------------------------------------------------------------------------------------------------------
	public JSONObject queryAction () {
		return mParentDevice.state();
	}

    //-----------------------------------------------------------------------------------------------------------------
    public void destroy(){
        mParentDevice.unregisterPanel(this);
    }

	//-----------------------------------------------------------------------------------------------------------------
	public Device device() { return mParentDevice; }

    //-----------------------------------------------------------------------------------------------------------------
    public void onStateChange(JSONObject _state) {}

    //-----------------------------------------------------------------------------------------------------------------
    public abstract  JSONObject serialize();

    //-----------------------------------------------------------------------------------------------------------------
    // private methods
    private void setCallbacks(){
        setClickCallback();
        setLongClickCallback();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setClickCallback(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { // Default onclick listener sends default panel command.
                Thread commThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject request = action();
                        if(request == null)
                            return;
                        device().setState(request);
                    }
                });
                commThread.start();
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setLongClickCallback(){
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 666 TODO: modify device menu.
                return false;
            }
        });
    }

    // Private members
    private Device mParentDevice;
	protected JSONObject mCommand;
	private boolean mIsPaused = false;

}
