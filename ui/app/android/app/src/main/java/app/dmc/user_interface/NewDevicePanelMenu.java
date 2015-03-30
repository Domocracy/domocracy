package app.dmc.user_interface;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-MAR-30
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class NewDevicePanelMenu {
    public NewDevicePanelMenu(Activity _activity){
        mMenuBuilder = new AlertDialog.Builder(_activity);

        createDialog(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void createDialog(final Activity _activity) {
        // Load devices and put them into the list
        setContentView(_activity);

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