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
import app.dmc.Room;
import app.dmc.User;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(Activity _activity) {
        setHub(_activity);
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(Activity _activity){
        mCurrentHub = User.get().getCurrentHub();
        mCurrentRoom = mCurrentHub.room("1");

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        mRoomSelector = new RoomSelector(_activity, mCurrentHub.rooms());

        ll.addView(mRoomSelector.view());
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub = null;
    private Room mCurrentRoom = null;

    // Views
    private RoomSelector mRoomSelector = null;
}
