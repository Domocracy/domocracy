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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import app.dmc.R;

public abstract class DevicePanel extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context){
        super(_context);
        mParentDevice = _dev;
        View.inflate(_context, _layoutResId, this);
        mIcon = (ImageView) findViewById(R.id.devIcon);

        setCallbacks();

        mDevName = (TextView) findViewById(R.id.devName);
        mDevName.setText(mParentDevice.name());
    }

    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context, boolean _isExtensible) {
        super(_context);
        mParentDevice = _dev;
        mIsExtensible = _isExtensible;

        View.inflate(_context, _layoutResId, this);

        mIcon = (ImageView) findViewById(R.id.devIcon);

        mShortLayout = (LinearLayout) findViewById(R.id.shortLayout);
        if (_isExtensible){
            mExtendButton = new ImageButton(_context);
            mExtendButton.setBackgroundResource(R.drawable.extend_button_selector);
            LayoutParams params = new LayoutParams(EXTEND_BUTTON_SIZE, EXTEND_BUTTON_SIZE);
            params.gravity = Gravity.CENTER_VERTICAL;
            mExtendButton.setLayoutParams(params);
            mShortLayout.addView(mExtendButton);
        }

        mDevName = (TextView) findViewById(R.id.devName);
        mDevName.setText(mParentDevice.name());

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
    protected void setIcon(final int _resource){
        mIcon.post(new Runnable() {
            @Override
            public void run() {
                mIcon.setImageResource(_resource);
            }
        });

    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setCallbacks(){
        setClickCallback();
        setLongClickCallback();
        if(mIsExtensible){
            setExtendCallback();
        }
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

    //-----------------------------------------------------------------------------------------------------------------
    private void setExtendCallback(){
        // 666 TODO: implement
    }

    // Private members
    private Device mParentDevice;
	protected JSONObject mCommand;
	private boolean mIsPaused = false;
    private boolean mIsExtensible = false;

    protected ImageView mIcon;
    private TextView mDevName;
    private LinearLayout mShortLayout;
    private ImageButton mExtendButton;

    private static final int EXTEND_BUTTON_SIZE = 100;
}
