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
import android.widget.Spinner;

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

        // Get Menu Holder
        mLateralMenuHolder = (DrawerLayout) ((Activity) _context).findViewById(R.id.base_layout);

    }

    //-----------------------------------------------------------------------------------------------------------------
    public void set(int _index){

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private interface
    private List<Hub> mHubList;

    // Views
    private DrawerLayout mLateralMenuHolder;
    private Spinner mHubSelector;
}
