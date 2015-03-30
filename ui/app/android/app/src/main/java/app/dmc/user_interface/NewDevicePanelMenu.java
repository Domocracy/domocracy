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

import app.dmc.Room;

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
        
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    private Room                mParentRoom;
}