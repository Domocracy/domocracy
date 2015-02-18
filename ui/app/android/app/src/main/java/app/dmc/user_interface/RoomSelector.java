///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.content.Context;
import android.view.View;

import java.util.List;

import app.dmc.Room;

public class RoomSelector {
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public RoomSelector(Context _context, List<Room>_rooms){

    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mView;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Private members

    private View mView = null;
}
