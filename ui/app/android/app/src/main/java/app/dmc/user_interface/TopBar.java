package app.dmc.user_interface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import app.dmc.HubManager;
import app.dmc.R;

/**
 * Created by Joscormir on 19/02/2015.
 */
public class TopBar {

    //-----------------------------------------------------------------------------------------------------------------
    public boolean setIpButton(Context _context){

            AlertDialog.Builder builderDialogSetIp = new AlertDialog.Builder(_context);
            LayoutInflater inflaterSetIp = (LayoutInflater)_context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);

            final View dialogLayout = inflaterSetIp.inflate(R.layout.alert_dialog_set_ip,null);
            builderDialogSetIp.setView(dialogLayout);
            EditText lastIp = (EditText)dialogLayout.findViewById(R.id.ipEditor);
            lastIp.setText(HubManager.get().hub("123").ip()); //666TODO: this need to be updated with user hub.
            builderDialogSetIp.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText ip = (EditText)dialogLayout.findViewById(R.id.ipEditor);

                    String ipToString = ip.getText().toString();
                    HubManager.get().hub("123").modifyIp(ipToString); //666TODO: this need to be updated with user hub.
                }
            });
            builderDialogSetIp.setNegativeButton("Cancel",null);
            builderDialogSetIp.create().show();
            return true;
        }
}
