///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//      Example of device for testing.

package app.dmc.devices;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.dmc.Hub;

public class SwitchDevice implements  Actuator{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public SwitchDevice(Hub _hub, Context _context){
        mHub = _hub;
        mCommands = new ArrayList<JSONObject>();
        decodeJson(_context);

    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View view(Context _context) {
        if(mView == null){
            //   Build dummy view to test JsonRequests
            LinearLayout base = new LinearLayout(_context);
            Button button = new Button(_context);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    run(mCommands.get(0));
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
        return mId;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public String id() {
        return mName;
    }


    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private String url(){
        return "http://localhost/aasd";    //666 TODO: get ip from Hub
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void decodeJson(Context _context){
        try {
            InputStream is = _context.getAssets().open("SwitchDevice.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String raw = new String(buffer, "UTF-8");
            JSONObject deviceInfo = new JSONObject(raw);

            mName = deviceInfo.getString("name");
            mId = deviceInfo.getString("id");

            JSONArray commands = deviceInfo.getJSONArray("commands");
            for(int i = 0; i < commands.length(); i++){
                mCommands.add(commands.getJSONObject(i));
            }

        }catch(JSONException _jsonException){
            _jsonException.printStackTrace();
        }catch (IOException _ioException){
            _ioException.printStackTrace();
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
