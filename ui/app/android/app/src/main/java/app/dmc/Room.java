///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.devices.Device;

public class Room {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public Room(JSONObject _data){
        try{
            mId         = _data.getString("id");
            mName       = _data.getString("name");

            JSONArray devices = _data.getJSONArray("devices");

            mDeviceList = new ArrayList<>();
            for(int i = 0; i < devices.length(); i++){
                mDeviceList.add(devices.getString(i));
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(Context _context, Hub _hub){
        if(mView == null){

            LinearLayout base = new LinearLayout(_context);
            base.setOrientation(LinearLayout.VERTICAL);

            TextView roomName = new TextView(_context);
            roomName.setText(name());
            roomName.setGravity(Gravity.CENTER_HORIZONTAL);
            roomName.setTextSize(50);
            base.addView(roomName);

            for(String deviceId:devices()){
                Device device = _hub.device(deviceId);
                if(device != null)
                    base.addView(device.view(_context));
            }

            mView = base;
        }

        return mView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String name(){
        return mName;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public String id(){
        return mId;

    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> devices(){
        return mDeviceList;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    // Identification
    private String mName;
    private String mId;

    List<String> mDeviceList;
    View mView;
}
