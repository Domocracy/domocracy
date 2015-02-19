package app.dmc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import app.dmc.core.Persistence;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Init HubManager
        Persistence.init(this);
        HubManager.init(this);

        HubManager.get().hub("123").modifyIp("10.100.5.7");

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
                builderDialogSetIp.setView();
                return true;

            default:
                return false;

        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Private interface.
    private UserInterface mUI;

   }
