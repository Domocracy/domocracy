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

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(Activity _activity, Hub _currentHub) {
        setHub(_activity, _currentHub);
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(Activity _activity, Hub _currentHub){
        mCurrentHub = _currentHub;
        mCurrentRoom = mCurrentHub.room("1");

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        ll.removeAllViews();

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
