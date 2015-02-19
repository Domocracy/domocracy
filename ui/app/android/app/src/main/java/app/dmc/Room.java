///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.devices.Device;

public class Room {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public Room(JSONObject _data, Hub _hub){
        mHub = _hub;
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
    public View view(Activity _activity){
        LinearLayout base = new LinearLayout(_activity);
        base.setOrientation(LinearLayout.VERTICAL);

        ImageView header = new ImageView(_activity);
        header.setImageResource(R.drawable.room_header);
        header.setAdjustViewBounds(true);
        base.addView(header);

        // 666 TODO: Class to admin actionbar
        android.support.v7.app.ActionBar topBar = ((ActionBarActivity) _activity).getSupportActionBar();
        topBar.setTitle(name());

        for(String deviceId:devices()){
            Device device = mHub.device(deviceId);
            if(device != null) {
                View v = device.view(_activity);

                // If device was added to another room, detach from parent
                ViewGroup parent = (ViewGroup) v.getParent();
                if(parent != null)
                    parent.removeView(v);

                // Add view to new parent
                base.addView(v);
            }
        }

        return base;
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
    private Hub mHub;

    List<String> mDeviceList;
}
