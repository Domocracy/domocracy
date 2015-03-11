///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.kodi;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.DevicePanel;

public class Kodi extends Actuator {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Kodi(JSONObject _devData){
        super(_devData);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, JSONObject _panelData, Context _context) {
        return new KodiLastShowPanel(this, _panelData, R.layout.kodi_last_show_panel, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void loadTvShows(){
        Thread queryShowsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Fill list with series
                JSONArray jsonShowList = commandQueryTvShows();
                try{
                    JSONObject state = new JSONObject();
                    state.put("state", jsonShowList);
                    // Update panels
                    updateState(state);
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
            }
        });
        queryShowsThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods


    // Commands
    private JSONArray commandQueryTvShows(){
        JSONObject request = new JSONObject();
        try{
            request.put("urlget","tvshows");
            request.put("method","GET");

        }
        catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        JSONObject response = runCommand(request);
        JSONArray jsonShowList;
        try{
            if(response != null)
                jsonShowList = response.getJSONArray("tvshows");
            else{
                jsonShowList = new JSONArray();
                JSONObject dummyShow = new JSONObject();

                dummyShow.put("tvshowid", -1);
                dummyShow.put("label", "KODI hasn't got TV shows");

                jsonShowList.put(dummyShow);
            }
        }
        catch (JSONException _jsonException){
            _jsonException.printStackTrace();
            return null;
        }

        return jsonShowList;
    }
}
