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

import app.dmc.R;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface


    public UserInterface(Activity _activity){
        _activity.setContentView(R.layout.activity_main);



        mMainScreen = new MainScreen(_activity);
        mLeftSideMenu = new SlideMenu(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(int _index){
        mLeftSideMenu.set(_index);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members


    //Views
    private MainScreen  mMainScreen;
    private SlideMenu   mLeftSideMenu;

}
