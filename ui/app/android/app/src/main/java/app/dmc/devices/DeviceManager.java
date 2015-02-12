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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.dmc.Hub;
import app.dmc.devices.supported_devices.HueLight;

public class DeviceManager {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public DeviceManager(JSONObject _devices){
        loadFactories();
        createDevices(_devices);
    }

    public Device getDevice(String _id){
        //  Check if device exist.
        if(mRegisteredDevices.containsKey(_id)){
            return mRegisteredDevices.get(_id);
        }
        // If not, return null.
        return null;

    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private void loadFactories(){
        mFactories.put("HueLight", new Factory() {
            @Override
            public Device create(Hub _hub, Context _context) {
                return new HueLight(_hub, _context);
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDevices(JSONObject _devices){
        Iterator<?> key = _devices.keys();
        while(key.hasNext()){
            mFactories.get(type).create(_hub, _context);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String, Factory> mFactories =  new HashMap<>();
    private Map<String, Device> mRegisteredDevices = new HashMap<>();

    //-----------------------------------------------------------------------------------------------------------------
    public interface Factory{
        Device create(Hub _hub, Context _context);
    }
}
