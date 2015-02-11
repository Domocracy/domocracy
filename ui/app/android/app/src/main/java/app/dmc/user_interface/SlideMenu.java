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
import android.widget.LinearLayout;

import java.util.List;

import app.dmc.Hub;
import app.dmc.R;

public class SlideMenu {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SlideMenu(Context _context, List<Hub> _hubList) {
        // Store Hub list.
        mHubList = _hubList;

        // Create a Spinner to store List of hubs.
        mHubSelector = new HubSelector(_context, _hubList);

        // Get Base Holder
        mBaseHolder = (DrawerLayout) ((Activity) _context).findViewById(R.id.base_layout);
        mLateralMenuHolder = (LinearLayout) ((Activity)_context).findViewById(R.id.left_menu);

        mLateralMenuHolder.addView(mHubSelector.view());

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(int _index){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private List<Hub>       mHubList;

    // Views
    private DrawerLayout    mBaseHolder;
    private LinearLayout    mLateralMenuHolder;
    private HubSelector     mHubSelector;
}
