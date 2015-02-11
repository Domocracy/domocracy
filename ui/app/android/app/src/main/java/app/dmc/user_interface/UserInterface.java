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

import app.dmc.Hub;
import app.dmc.R;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public UserInterface(Context _context, Hub _hub){
        ((Activity)_context).setContentView(R.layout.activity_main);

        mMainScreen = new MainScreen(_context,_hub);
        mLateralMenu = new SlideMenu(_context,_hub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(Hub _hub){
        mCurrentHub = _hub;
        mMainScreen.set(_hub);
        mLateralMenu.set(_hub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members
    private MainScreen  mMainScreen;
    private SlideMenu   mLateralMenu;

    private Hub mCurrentHub;
}
