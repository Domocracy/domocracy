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
import app.dmc.HubManager;
import app.dmc.R;
import app.dmc.devices.DeviceManager;
import app.dmc.devices.supported_devices.HueLight;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(Activity _activity) {
        HubManager hubMgr = HubManager.get();
        mCurrentHub = hubMgr.hub(hubMgr.defaultHub());

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        /*HueLight hue = (HueLight) DeviceManager.get().device("1");

        if(hue != null)
            ll.addView(hue.view(_activity));*/

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub = null;
    // Views
}
