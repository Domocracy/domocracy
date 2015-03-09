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
import app.dmc.R;

public class SlideMenu {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public SlideMenu(Activity _activity) {
        // Get Base Layout
        mLayout = (LinearLayout) _activity.findViewById(R.id.left_menu);
        // Create a Spinner to store List of hubs.
        mHubSelector = new HubSelector(_activity);
        mLayout.addView(mHubSelector.view());
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Views
    private LinearLayout    mLayout;
    private HubSelector     mHubSelector;

}
