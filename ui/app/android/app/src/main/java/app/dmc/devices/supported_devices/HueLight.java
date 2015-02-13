///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.Hub;
import app.dmc.devices.Actuator;
import app.dmc.devices.JsonRequest;

public class HueLight implements Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public HueLight(JSONObject _data, Context _context){
        //mHub = HubManager.get(); get hub from manager
        mCommands = new ArrayList<>();
        decodeJson(_data);

    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            //   Build dummy view to test JsonRequests
            LinearLayout base = new LinearLayout(_context);
            Button button = new Button(_context);

            button.setText(name());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //run(mCommands.get(0));    666 Design commands.
                }
            });

            base.addView(button);
            mView = base;
        }
        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void run(JSONObject _jsonCommand) {
        // Create a new request with own url and using a json.
        final JSONObject command = _jsonCommand;
        Thread t = new Thread( new Runnable() {
            @Override
            public void run() {
                JsonRequest request = new JsonRequest(url(), command);
            }
        });
        t.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String name() {
        return mName;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return  mId;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private String url(){
        return "http://10.100.5.16/sdasd";

        // decode url properly
        //return "http://" + mHub.name() +"/" + name() +"/";    //666 TODO: get ip from Hub
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void decodeJson(JSONObject _data){
        try {
            mName = _data.getString("name");
            mId = _data.getString("id");

        }catch(JSONException _jsonException) {
            _jsonException.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String mName;
    private String mId;
    private Hub mHub;

    private List<JSONObject> mCommands;
    private View mView;
}
