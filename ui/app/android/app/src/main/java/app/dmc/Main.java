package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import app.dmc.user_interface.UserInterface;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUI = new UserInterface(this);
        setContentView(mUI.build(this));


    }


    //-----------------------------------------------------------------------------------------------------------------
    UserInterface mUI;
}
