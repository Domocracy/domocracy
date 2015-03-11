///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;

import app.dmc.Hub;
import app.dmc.R;
import app.dmc.Room;

public class MainScreen {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public MainScreen(ActionBarActivity _activity, Hub _currentHub) {
        setHub(_activity, _currentHub);
    }
    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(ActionBarActivity _activity, Hub _currentHub){
        mCurrentHub = _currentHub;

        mCurrentRoom = mCurrentHub.room(mCurrentHub.rooms().get(0).id());

        LinearLayout ll = (LinearLayout) _activity.findViewById(R.id.main_screen);

        ll.removeAllViews();

        mRoomSelector = new RoomSelector(_activity, mCurrentHub.rooms());

        ll.addView(mRoomSelector.view());
        if(mCurrentRoom != null)
        setRoomTitle(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setRoomTitle(ActionBarActivity _activity){
        ActionBar ab = _activity.getSupportActionBar();
        ab.setTitle(mCurrentRoom.name());
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub = null;
    private Room mCurrentRoom = null;

    // Views
    private RoomSelector mRoomSelector = null;
}
