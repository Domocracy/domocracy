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
import app.dmc.Room;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(Activity _activity) {
        HubManager hubMgr = HubManager.get();
        mCurrentHub = hubMgr.hub(hubMgr.defaultHub());
        mCurrentRoom = mCurrentHub.room("1");

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        mRoomSelector = new RoomSelector(_activity, mCurrentHub.rooms());

        ll.addView(mRoomSelector.view());
        //.addView(mCurrentRoom.view(_activity));
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
    private RoomSelector mRoomSelector = null;
}
