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

import java.util.List;
import java.util.Map;

import app.dmc.Hub;
import app.dmc.R;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public UserInterface(Context _context, List<String> _hubList, int _defaultHub){
        ((Activity)_context).setContentView(R.layout.activity_main);

        mMainScreen = new MainScreen(_context,_hubList);
        mLateralMenu = new SlideMenu(_context,_hubList);
        mMainScreen.set(_defaultHub);
        mLateralMenu.set(_defaultHub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(int _index){
        mMainScreen.set(_index);
        mLateralMenu.set(_index);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members
    private Hub mHubList;

    //Views
    private MainScreen  mMainScreen;
    private SlideMenu   mLateralMenu;

}
