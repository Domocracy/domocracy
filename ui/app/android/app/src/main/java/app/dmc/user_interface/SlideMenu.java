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
import android.support.v4.widget.DrawerLayout;

import app.dmc.Hub;
import app.dmc.R;

public class SlideMenu {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SlideMenu(Context _context, Hub _hub) {
        mCurrentHub = _hub;

        mLateralMenuHolder = (DrawerLayout) ((Activity) _context).findViewById(R.id.base_layout);

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(Hub _hub){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private Hub mCurrentHub;
    private DrawerLayout mLateralMenuHolder;
}
