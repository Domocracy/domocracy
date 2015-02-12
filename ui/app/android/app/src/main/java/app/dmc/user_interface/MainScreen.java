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
import android.widget.LinearLayout;

import app.dmc.Hub;
import app.dmc.R;
import app.dmc.devices.SwitchDevice;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public MainScreen(Activity _activity, Hub _hub) {
        mHub = _hub;

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        SwitchDevice b2 = new SwitchDevice(mHub, _activity);
        SwitchDevice b1 = new SwitchDevice(mHub, _activity);

        ll.addView(b1.view(_activity));
        ll.addView(b2.view(_activity));
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(int _index){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mHub;

    // Views
}
