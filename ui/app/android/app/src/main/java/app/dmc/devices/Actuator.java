///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;

import org.json.JSONObject;

import app.dmc.Hub;
import app.dmc.HubManager;

public abstract class Actuator extends Device {
    //-----------------------------------------------------------------------------------------------------------------
    protected Actuator(JSONObject _devData){
        super(_devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject runCommand(final JSONObject _jsonCommand) {
        Hub hub = HubManager.get().hub(hub());
        return hub.send("/device/" + id(), _jsonCommand);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract JSONObject action(JSONObject _stateInfo);

}
