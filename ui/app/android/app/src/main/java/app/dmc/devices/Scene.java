///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-19
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.devices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;

public class Scene implements Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    Scene(JSONObject _data){
        try {
            mName = _data.getString("name");
            mId = _data.getString("id");
            mHubId = _data.getString("hub");

        }catch(JSONException _jsonException) {
            _jsonException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            LayoutInflater inflater = LayoutInflater.from(_context);
            LinearLayout base = (LinearLayout) inflater.inflate(R.layout.scene_layout, null);

            

        }

        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String name() {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void runCommand(JSONObject _jsonCommand) {
        Hub hub = HubManager.get().hub(mHubId);
        hub.send("/device/" + id(), _jsonCommand);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members

    // Identification
    private String mName;
    private String mId;
    private String mHubId;

    // Content
    private View mView;

    private Map<Actuator, JSONObject> mCommands;

    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
}
