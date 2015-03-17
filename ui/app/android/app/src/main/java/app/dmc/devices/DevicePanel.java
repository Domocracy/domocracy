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
import android.widget.TextView;

import org.json.JSONObject;

import app.dmc.R;

public abstract class DevicePanel extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context){
        super(_context);
        mParentDevice = _dev;

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

        View.inflate(_context, _layoutResId, this);

        mDevName = (TextView) findViewById(R.id.devName);
        mDevName.setText(mParentDevice.name());
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
    // Private members
    Device mParentDevice;
	protected JSONObject mCommand;
	boolean mIsPaused = false;

    private TextView mDevName;
}
