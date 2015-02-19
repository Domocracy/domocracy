package app.dmc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Init HubManager
        Persistence.init(this);
        HubManager.init(this);



        // Create Interface
        // Check if first connection
        //      Launch firstConnectionInterface
        //  else
        //      Init user interface
        //      Init Connections
        //      so on...


        mUI = new UserInterface(this);

    }
    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    //-----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.set_ip:
                AlertDialog.Builder builderDialogSetIp = new AlertDialog.Builder(this);
                LayoutInflater inflaterSetIp = this.getLayoutInflater();
                final View dialogLayout = inflaterSetIp.inflate(R.layout.alert_dialog_set_ip,null);
                builderDialogSetIp.setView(dialogLayout);
                EditText lastIp = (EditText)dialogLayout.findViewById(R.id.ipEditor);
                lastIp.setText(HubManager.get().hub("123").ip());
                builderDialogSetIp.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText ip = (EditText)dialogLayout.findViewById(R.id.ipEditor);

                        String ipToString = ip.getText().toString();
                        HubManager.get().hub("123").modifyIp(ipToString);
                    }
                });
                builderDialogSetIp.setNegativeButton("Cancel",null);
                builderDialogSetIp.create().show();
                return true;

            default:
                return false;

        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Private interface.
    private UserInterface mUI;

   }
