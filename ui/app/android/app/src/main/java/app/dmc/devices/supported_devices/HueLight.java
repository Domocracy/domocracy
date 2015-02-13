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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            Button buttonON = new Button(_context);
            Button buttonOFF = new Button(_context);

            buttonON.setText("ON");
            buttonON.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject command = mCommands.get(0);

                    try {
                        // Create State.
                        JSONObject state = new JSONObject();
                        state.put("on", true);
                        Random r = new Random();
                        state.put("hue", r.nextInt(65535));
                        //Create body and fill it with JSON.
                        JSONObject body = new JSONObject();
                        body.put("State", state);
                        // Add body to command.
                        command.put("body", body.toString());
                    }catch(JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }

                    run(command);
                }
            });

            buttonOFF.setText("OFF");
            buttonOFF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject command = mCommands.get(1);

                    try {
                        // Create State.
                        JSONObject state = new JSONObject();
                        state.put("on", true);
                        Random r = new Random();
                        state.put("hue", r.nextInt(65535));
                        //Create body and fill it with JSON.
                        JSONObject body = new JSONObject();
                        body.put("State", state);
                        // Add body to command.
                        command.put("body", body.toString());
                    }catch(JSONException _jsonException){
                        _jsonException.printStackTrace();
                    }

                    run(command);
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
    public void run(JSONObject _jsonCommand) {
        // Create a new request with own url and using a json.
        final JSONObject command = _jsonCommand;
        Thread t = new Thread( new Runnable() {
            @Override
            public void run() {
                JsonRequest request = new JsonRequest(url() , command);
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
        String hubUrl = "http://10.200.8.169";
        String userUrl = "dmc64";
        return hubUrl + "/user/" + userUrl + "/device/" + id();

        // decode url properly
        //return "http://" + mHub.name() +"/" + name() +"/";    //666 TODO: get ip from Hub
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void decodeJson(JSONObject _data){
        try {
            mName = _data.getString("name");
            mId = _data.getString("id");

            JSONArray commands = _data.getJSONArray("commands");
            for(int i = 0; i < commands.length(); i++){
                mCommands.add(commands.getJSONObject(i));
            }

        }catch(JSONException _jsonException) {
            _jsonException.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Identification
    private String mName;
    private String mId;

    private List<JSONObject> mCommands;
    private View mView;
}
