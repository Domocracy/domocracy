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
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;
import app.dmc.Room;
import app.dmc.devices.Device;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(Activity _activity) {
        HubManager hubMgr = HubManager.get();
        mCurrentHub = hubMgr.hub(hubMgr.defaultHub());
        mCurrentRoom = hubMgr.hub(hubMgr.defaultHub()).room("1");

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        TextView roomName = new TextView(_activity);
        roomName.setText(mCurrentRoom.name());
        roomName.setGravity(Gravity.CENTER_HORIZONTAL);
        roomName.setTextSize(50);
        ll.addView(roomName);

        for(String deviceId:mCurrentRoom.devices()){
            Device device = mCurrentHub.device(deviceId);
            if(device != null)
                ll.addView(device.view(_activity));
        }
/*
        HueLight hue = (HueLight) mCurrentHub.device("2A");
        if(hue != null)
            ll.addView(hue.view(_activity));


        hue = (HueLight) mCurrentHub.device("2");
        if(hue != null)
            ll.addView(hue.view(_activity));*/

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub = null;
    private Room mCurrentRoom = null;

    // Views
}
