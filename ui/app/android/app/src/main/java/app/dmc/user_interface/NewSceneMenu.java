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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.dmc.User;

public class NewSceneMenu extends DialogFragment{
    public NewSceneMenu(){
        mMenuBuilder = new AlertDialog.Builder(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Load devices and put them into the list
        setContentView();
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
    private void setContentView(){
        LinearLayout layout = new LinearLayout(getActivity());
        TextView title = new TextView(getActivity());
        title.setText("Add new scene");
        layout.addView(title);

        mDevList = new ListView(getActivity());
        List<String> devicesId = User.get().getCurrentHub().devicesId();

        for(int i = 0; i < devicesId.size(); i++){
            CheckBox devCheckBox = new CheckBox(getActivity());
            devCheckBox.setText(User.get().getCurrentHub().device(devicesId.get(i)).name());
        }

        mMenuBuilder.setView(layout);
    }

    private void addScene(){
        
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    ListView mDevList;


}
