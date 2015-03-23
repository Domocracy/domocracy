///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.devices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
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

            mExtenView = (LinearLayout) findViewById(R.id.extendedLayout);
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
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCallback();
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onLongClickCallback();
            }
        });

        if(mIsExtensible){
            mExtendButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExtendCallback();
                }
            });;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    protected void onClickCallback(){
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

    //-----------------------------------------------------------------------------------------------------------------
    protected boolean onLongClickCallback(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Change Name")
                .setView(new EditText(this.getContext()))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Change Name;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Intentionally blank.
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    protected void onExtendCallback(){
        float iniY = -1;
        Animation slideDown = new TranslateAnimation(   Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, iniY,
                Animation.RELATIVE_TO_SELF, 0);
        slideDown.setDuration(400);

        switch (mExtenView.getVisibility()){
            case View.VISIBLE:
                mExtenView.setVisibility(View.GONE);
                mExtendButton.setBackgroundResource(R.drawable.extend_button_selector);
                break;
            case View.GONE:
                mExtenView.setAnimation(slideDown);
                mExtenView.setVisibility(View.VISIBLE);
                mExtendButton.setBackgroundResource(R.drawable.collapse_button_selector);
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Private members
    private Device mParentDevice;
	protected JSONObject mCommand;
	private boolean mIsPaused = false;
    private boolean mIsExtensible = false;

    protected ImageView mIcon;
    protected TextView mDevName;
    protected LinearLayout mShortLayout;
    protected LinearLayout mExtenView;
    protected ImageButton mExtendButton;

    private static final int EXTEND_BUTTON_SIZE = 100;

}

