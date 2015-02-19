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
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.util.Map;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;

public class Scene implements Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public Scene(JSONObject _data){
        // 666 Load from JSON
        mName = "All off";// _data.getString("name");
        mId = "3A";//_data.getString("id");
        mHubId = "123";//_data.getString("hub");

    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            LayoutInflater inflater = LayoutInflater.from(_context);
            LinearLayout base = (LinearLayout) inflater.inflate(R.layout.scene_layout, null);

            ToggleButton button = (ToggleButton) base.findViewById(R.id.toggleButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCommand(new JSONObject());
                        }
                    });
                    t.start();
                }
            });

            mView = base;

        }

        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String name() {
        return mName;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return mId;
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
