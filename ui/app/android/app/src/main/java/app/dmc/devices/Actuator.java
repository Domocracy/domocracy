///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.Hub;
import app.dmc.HubManager;

public abstract class Actuator extends Device {
    //-----------------------------------------------------------------------------------------------------------------
    protected Actuator(JSONObject _devData){
        super(_devData);
    }
}
