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
import android.widget.Button;
import android.widget.LinearLayout;

import app.dmc.Hub;

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
        View xmlView = inflater.inflate(R.layout.activity_main, null);*/

        LinearLayout ll = new LinearLayout(_context);
        Button b = new Button(_context);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mCurrentHub.send(JSONObject _json);
            }
        });
        b.setText("Push me");
        ll.addView(b);

        return  ll;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Members
    private MainScreen  mMainScreen;
    private SlideMenu   mLateralMenu;

    private Hub mCurrentHub;
}
