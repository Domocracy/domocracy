package app.dmc.user_interface;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-MAR-30
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

import android.app.AlertDialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.dmc.Room;
import app.dmc.User;

public class NewDevicePanelMenu {
    public NewDevicePanelMenu(Context _context, Room _room){
        mMenuBuilder = new AlertDialog.Builder(_context);
        mParentRoom = _room;

        createDialog(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void createDialog(Context _context) {
        // Load devices and put them into the list
        setContentView(_context);
        mMenuBuilder.setTitle("Choose device to be added:");

        mMenuBuilder.create().show();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setContentView(Context _context){
        LinearLayout layout = new LinearLayout(_context);
        layout.setOrientation(LinearLayout.VERTICAL);
        List<String> devices = User.get().getCurrentHub().deviceIds();
        for(String id :devices){
            TextView tv = new TextView(_context);
            tv.setText(User.get().getCurrentHub().device(id).name());
            layout.addView(tv);
        }
        mMenuBuilder.setView(layout);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    private Room                mParentRoom;
}