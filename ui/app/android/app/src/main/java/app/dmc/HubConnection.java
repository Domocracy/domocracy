///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-16
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc;

import org.json.JSONObject;

import app.dmc.devices.JsonRequest;

public class HubConnection {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public JSONObject send(final String _url, final String _body){
        final JsonRequest request = new JsonRequest();
        request.setMethod("PUT");
        request.setBody(_body);

        request.sendRequest(_url);

        return null;    // 666 Implement response
    }
    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject get(final String _url){
        final JsonRequest request = new JsonRequest();
        request.setMethod("GET");

        request.sendRequest(_url);

        return null;    // 666 Implement response
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface
}
