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
import android.view.View;
import android.widget.LinearLayout;

import app.dmc.Hub;
import app.dmc.devices.SwitchDevice;

public class UserInterface {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public UserInterface(Activity _activity){
        mMainScreen = new MainScreen(_activity);
        mLateralMenu = new SlideMenu(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void setHub(Hub _hub){
        mCurrentHub = _hub;
        mMainScreen.set(_hub);
        mLateralMenu.set(_hub);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View build(Context _context){
        // Dummy build
        /*LayoutInflater inflater = LayoutInflater.from(_context);
        return inflater.inflate(R.layout.activity_main, null);*/

        LinearLayout ll = new LinearLayout(_context);

        SwitchDevice b2 = new SwitchDevice(mCurrentHub);
        SwitchDevice b1 = new SwitchDevice(mCurrentHub);

        ll.addView(b1.view(_context));
        ll.addView(b2.view(_context));
        return ll;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members
    private MainScreen  mMainScreen;
    private SlideMenu   mLateralMenu;

    private Hub mCurrentHub;
}
