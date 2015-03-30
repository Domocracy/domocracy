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
import android.content.DialogInterface;

public class NewDevicePanelMenu {
    public NewDevicePanelMenu(Context _context){
        mMenuBuilder = new AlertDialog.Builder(_context);

        createDialog(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void createDialog(Context _context) {
        // Load devices and put them into the list
        setContentView(_context);

        mMenuBuilder.setTitle("New Panel");
        // Buttons
        mMenuBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Intentionally blank
            }
        });
        mMenuBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        mMenuBuilder.create().show();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setContentView(Context _context){
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
}