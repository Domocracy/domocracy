package app.dmc.devices;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-16
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.dmc.devices.supported_devices.HueLight;
import app.dmc.devices.supported_devices.Kodi;

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
    public Device create(String _type, JSONObject _data){
        return sFactories.get(_type).create(_data);
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
            public Device create(JSONObject _data) {
                return new HueLight(_data);
            }
        });

        // Scene Factory
        sFactories.put("Scene", new Factory() {
            @Override
            public Device create(JSONObject _data) {
                return new Scene(_data);
            }
        });

        // Kodi Factory
        sFactories.put("Kodi", new Factory() {
            @Override
            public Device create(JSONObject _data) {
                return new Kodi(_data);
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
        Device create(JSONObject _data);
    }
}
