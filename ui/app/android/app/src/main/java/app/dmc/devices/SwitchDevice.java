///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//      Example of device for testing.

package app.dmc.devices;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class SwitchDevice implements  Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    @Override
    public View view(Context _context) {
        if(mView == null){
            LinearLayout base = new LinearLayout(_context);
            ToggleButton button = new ToggleButton(_context);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    run();
                }
            });

            base.addView(button);
            mView = base;
        }
        return mView;
    }

    @Override
    public void run() {
        Log.d("DEBUG", "I'm " + (mState ? "ON" : "OFF"));
        mState = !mState;
    }

    @Override
    public String name() {
        return mId;
    }

    @Override
    public String id() {
        return mName;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private View mView;
    private String mName;
    private String mId;
    private boolean mState = true;

}
