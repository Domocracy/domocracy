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

import app.dmc.devices.supported_devices.HueLight;

public class DeviceManager {
    //-----------------------------------------------------------------------------------------------------------------
    //  Singleton interface Interface
    static public void init(Context _context, JSONObject _devData){
        if(sDevMgr == null)
            sDevMgr = new DeviceManager(_context, _devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    static public DeviceManager get(){
        return sDevMgr;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Device device(String _id){
        //  Check if device exist.
        if(sRegisteredDevices.containsKey(_id)){
            return sRegisteredDevices.get(_id);
        }
        // If not, return null.
        return null;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private DeviceManager(Context _context, JSONObject _devData){
        loadFactories();
        createDevices(_context, _devData);
    }

    private void loadFactories(){
        // HueLight Factory
        sFactories.put("HueLight", new Factory() {
            @Override
            public Device create(JSONObject _data, Context _context) {
                return new HueLight(_data, _context);
            }
        });

        // Add other Factories
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDevices(Context _context, JSONObject _devData){
        try{
            JSONArray devices = _devData.getJSONArray("devices");

            for(int i = 0; i < devices.length(); i++){
                // Extract device type and data
                JSONObject deviceData = devices.getJSONObject(i);
                String type = deviceData.getString("type");
                JSONObject data = deviceData.getJSONObject("data");

                // Look for factory and create device
                sFactories.get(type).create(data, _context);
            }

        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    static private DeviceManager sDevMgr = null;
    static private Map<String, Factory> sFactories =  new HashMap<>();
    static private Map<String, Device> sRegisteredDevices = new HashMap<>();

    //-----------------------------------------------------------------------------------------------------------------
    public interface Factory{
        Device create(JSONObject _data, Context _context);
    }
}
