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
import java.util.Map;

import app.dmc.Hub;
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
            public Device create(Hub _hub, Context _context) {
                return new HueLight(_hub, _context);
            }
        });

        // Add other Factories
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDevices(Context _context, JSONObject _devData){

    }

    //-----------------------------------------------------------------------------------------------------------------
    static private DeviceManager sDevMgr = null;
    static private Map<String, Factory> sFactories =  new HashMap<>();
    static private Map<String, Device> sRegisteredDevices = new HashMap<>();

    //-----------------------------------------------------------------------------------------------------------------
    public interface Factory{
        Device create(Hub _hub, Context _context);
    }
}
