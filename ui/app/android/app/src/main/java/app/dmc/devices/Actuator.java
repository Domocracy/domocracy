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

    //-----------------------------------------------------------------------------------------------------------------
    final public JSONObject runCommand(final JSONObject _request) {
        Hub hub = HubManager.get().hub(hub());
        try{
            String method = _request.getString("method");
            if(method.equals("GET"))
                return hub.get("/device/" + id() + "/" + _request.getString("urlget"));
            if(method.equals("PUT"))
                return hub.send("/device/" + id(), _request.getJSONObject("cmd"));

        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract JSONObject action(JSONObject _stateInfo);

}
