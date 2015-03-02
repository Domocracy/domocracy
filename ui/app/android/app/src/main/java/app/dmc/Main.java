package app.dmc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.core.Persistence;
import app.dmc.user_interface.TopBar;
import app.dmc.user_interface.UserInterface;

public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Init HubManager
        Persistence.init(this);
        HubManager.init(this);

        try {
            mPrueba = new JSONObject("{\"defaultHub\":\"0\",\"hubs\":[{\"name\":\"Home\",\"id\":\"123\",\"ip\":\"193.147.168.23\",\"rooms\":[],\"devices\":[]},{\"name\":\"Beach Flat\",\"id\":\"543\",\"ip\":\"193.154.123.54\",\"rooms\":[],\"devices\":[]}]}");

        }catch(JSONException e) {
            e.printStackTrace();
        }
        mPruebaRecieved = Persistence.get().getJSON("hubList");
        Persistence.get().flush();
        Persistence.get().end();
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
        TopBar topBar = new TopBar();
        switch(item.getItemId()){
            case R.id.set_ip:
                if(topBar.setIpButton(this))
                return true;
            default:
                return false;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------

    // Private interface.
    private UserInterface mUI;

    private Object mPruebaRecieved = null;
    private JSONObject mPrueba;
   }

