///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public DeviceManager(JSONArray _devData) {
        createDevices(_devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public Device register(JSONObject _deviceInfo){
        DeviceFactory factory = DeviceFactory.get();
        try {
            String type = _deviceInfo.getString("type");
            Device dev = factory.create(type, _deviceInfo);
            mRegisteredDevices.put(dev.id(), dev);

            return dev;
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public List<String> deviceIds(){
        List<String> ids = new ArrayList<>();
        for(Device dev: mRegisteredDevices.values()){
            ids.add(dev.id());
        }
        return ids;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface

    //-----------------------------------------------------------------------------------------------------------------
    private void createDevices(JSONArray _devData) {
        try {
            for (int i = 0; i < _devData.length(); i++) {
                // Extract device type and data
                JSONObject deviceData = _devData.getJSONObject(i);
                String type = deviceData.getString("type");
                JSONObject data = deviceData.getJSONObject("data");

                // Look for factory and create device
                DeviceFactory factory = DeviceFactory.get();
                Device dev = factory.create(type, data);
                mRegisteredDevices.put(dev.id(), dev);
            }

        } catch (JSONException _jsonException) {
            _jsonException.printStackTrace();
        }

    }

    private Map<String, Device> mRegisteredDevices = new HashMap<>();
}
