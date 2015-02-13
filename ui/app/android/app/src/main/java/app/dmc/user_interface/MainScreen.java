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

import java.util.List;
import java.util.Map;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;
import app.dmc.devices.SwitchDevice;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public MainScreen(Context _context, List<String> _hubList) {
        mHubList = _hubList;

        LinearLayout ll = (LinearLayout) ((Activity)_context).findViewById(R.id.main_screen);

        SwitchDevice b2 = new SwitchDevice(HubManager.getInstance().getHub(_hubList.get(0)), _context);

        SwitchDevice b1 = new SwitchDevice(HubManager.getInstance().getHub(_hubList.get(0)), _context);

        ll.addView(b1.view(_context));
        ll.addView(b2.view(_context));
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(int _index){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private List<String> mHubList;

    // Views
}
