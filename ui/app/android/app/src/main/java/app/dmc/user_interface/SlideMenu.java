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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import app.dmc.R;

public class SlideMenu {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public SlideMenu(final Activity _activity, List<String> _hubIdList) {
        // Get Base Layout
        mLayout = (LinearLayout) _activity.findViewById(R.id.left_menu);
        // Create a Spinner to store List of hubs.
        mHubSelector = new HubSelector(_activity, _hubIdList);
        mLayout.addView(mHubSelector.view());

        Button newSceneButton = new Button(_activity);
        newSceneButton.setText("Add new scene");
        newSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewSceneMenu newSceneMenu = new NewSceneMenu(_activity);
            }
        });
        mLayout.addView(newSceneButton);

        Button newRoomButton = new Button(_activity);
        newRoomButton.setText("Add new room");
        newRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRoomMenu newRoomMenu = new NewRoomMenu(_activity);
            }
        });
        mLayout.addView(newRoomButton);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Views
    private LinearLayout    mLayout;
    private HubSelector     mHubSelector;

}
