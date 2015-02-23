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

public abstract class Actuator extends Device {
    protected Actuator(JSONObject _devData){
        super(_devData);
    }

    public abstract void runCommand(final JSONObject _jsonCommand);
    public abstract JSONObject action(JSONObject _stateInfo);
}
