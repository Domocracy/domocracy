///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import app.dmc.Hub;
import app.dmc.R;
import app.dmc.devices.SwitchDevice;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public MainScreen(Context _context, Hub _hub) {
        mCurrentHub = _hub;

        LinearLayout ll = (LinearLayout) ((Activity)_context).findViewById(R.id.main_screen);

        SwitchDevice b2 = new SwitchDevice(mCurrentHub);
        SwitchDevice b1 = new SwitchDevice(mCurrentHub);

        ll.addView(b1.view(_context));
        ll.addView(b2.view(_context));
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(Hub _hub){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub;
}
