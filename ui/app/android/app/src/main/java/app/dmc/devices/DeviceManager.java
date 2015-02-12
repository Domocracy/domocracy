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
import java.util.List;
import java.util.Map;

import app.dmc.Hub;
import app.dmc.devices.supported_devices.HueLight;

public class DeviceManager {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public DeviceManager(Context _context, List<Hub> _hubList, JSONObject _devices){
        loadFactories();
        createDevices(_context, _hubList);
    }

    //-----------------------------------------------------------------------------------------------------------------
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
    private void createDevices(Context _context, List<Hub> _hubList){
        //for(Hub hub : _hubList) {
        //    while (key.hasNext()) {
        //        int hub = 0;                // 666 TODO: get hub from data
        //        String type = "HueLight";   // 666 TODO: get type from data.
        //        mFactories.get(type).create(_hubList.get(hub), _context);
        //    }
        //}
    }

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String, Factory> mFactories =  new HashMap<>();
    private Map<String, Device> mRegisteredDevices = new HashMap<>();

    //-----------------------------------------------------------------------------------------------------------------
    public interface Factory{
        Device create(Hub _hub, Context _context);
    }
}
