package app.dmc.devices;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-16
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.dmc.devices.supported_devices.HueLight;

public class DeviceFactory {
    //-----------------------------------------------------------------------------------------------------------------
    // Static Interface
    static public DeviceFactory get() {
        if(sDevFactory == null)
            sDevFactory = new DeviceFactory();

        return sDevFactory;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Device create(String _type, JSONObject _data, Context _context){
        return sFactories.get(_type).create(_data, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
    private DeviceFactory() {
        loadFactories();
    }

    private void loadFactories() {
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
    // Members
    static private DeviceFactory sDevFactory = null;
    static private Map<String, Factory> sFactories =  new HashMap<>();

    //-----------------------------------------------------------------------------------------------------------------
    public interface Factory{
        Device create(JSONObject _data, Context _context);
    }
}
