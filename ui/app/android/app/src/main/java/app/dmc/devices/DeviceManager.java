///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeviceManager {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Device device(String _id) {
        //  Check if device exist.
        if (mRegisteredDevices.containsKey(_id)) {
            return mRegisteredDevices.get(_id);
        }
        // If not, return null.
        return null;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private DeviceManager(Context _context, JSONObject _devData) {
        createDevices(_context, _devData);
    }


    //-----------------------------------------------------------------------------------------------------------------
    private void createDevices(Context _context, JSONObject _devData) {
        try {
            JSONArray devices = _devData.getJSONArray("devices");

            for (int i = 0; i < devices.length(); i++) {
                // Extract device type and data
                JSONObject deviceData = devices.getJSONObject(i);
                String type = deviceData.getString("type");
                JSONObject data = deviceData.getJSONObject("data");

                // Look for factory and create device
                DeviceFactory factory = DeviceFactory.get();
                Device dev = factory.create(type, data, _context);
                mRegisteredDevices.put(dev.id(), dev);
            }

        } catch (JSONException _jsonException) {
            _jsonException.printStackTrace();
        }

    }

    private Map<String, Device> mRegisteredDevices = new HashMap<>();
}
