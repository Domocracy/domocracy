///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-MAR-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.user_interface;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NewSceneMenu extends DialogFragment{
    public NewSceneMenu(){
        mMenuBuilder = new AlertDialog.Builder(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Load devices and put them into the list

        // Set add button
        mMenuBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addScene();
            }
        });

        return mMenuBuilder.create();
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void addScene(){

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;


}
