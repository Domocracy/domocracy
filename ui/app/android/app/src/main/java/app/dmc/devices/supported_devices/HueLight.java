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

import java.util.Random;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.devices.Actuator;

public class HueLight implements Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public HueLight(JSONObject _data, Context _context){
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
            //   Build dummy view to test JsonRequests
            LinearLayout base = new LinearLayout(_context);
            Button buttonON = new Button(_context);
            Button buttonOFF = new Button(_context);

            buttonON.setText("ON");
            buttonON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject body = new JSONObject();
                    try {
                        // Create State.
                        JSONObject state = new JSONObject();
                        state.put("on", true);
                        Random r = new Random();
                        state.put("hue", r.nextInt(65535));
                        //Create body and fill it with JSON.
                        body.put("State", state);
                    }catch(JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }
                    final JSONObject fBody = body;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCommand(fBody);
                        }
                    });
                    t.start();
                }
            });

            buttonOFF.setText("OFF");
            buttonOFF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject body = new JSONObject();
                    try {
                        // Create State.
                        JSONObject state = new JSONObject();
                        state.put("on", false);
                        Random r = new Random();
                        state.put("hue", r.nextInt(65535));
                        //Create body and fill it with JSON.
                        body = state;
                    }catch(JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }
                    final JSONObject fBody = body;
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runCommand(fBody);
                        }
                    });
                    t.start();
                }
            });

            base.addView(buttonON);
            base.addView(buttonOFF);

            mView = base;
        }
        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void runCommand(final JSONObject _jsonCommand) {
        // Create a new request with own url and using a json.
        Hub hub = HubManager.get().hub(mHubId);
        hub.send("/device/" + id(), _jsonCommand);
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

    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String mName;
    private String mId;

    private String mHubId;
    private View mView;
}
