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

import java.util.List;
import java.util.Map;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface

    public UserInterface(Activity _activity, List<String> _hubList, String _defaultHub){
        mHubList = _hubList;
        _activity.setContentView(R.layout.activity_main);


        mMainScreen = new MainScreen(_activity, mHubList);
        mLeftSideMenu = new SlideMenu(_activity, mHubList, _defaultHub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(int _index){
        mLeftSideMenu.set(_index);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members

    private List<String> mHubList;

    //Views
    private MainScreen  mMainScreen;

    private SlideMenu   mLeftSideMenu;

}
