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
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class Kodi extends Device {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Kodi(JSONObject _devData){
        super(_devData);
        mMovieDataList  = new JSONArray();
        mTvShowDataList = new JSONArray();

        try {
            JSONObject mediaData = _devData.getJSONObject("media");
            mMovieDataList = mediaData.getJSONArray("movies");
            mTvShowDataList = mediaData.getJSONArray("tvshows");
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        return new KodiLastShowPanel(this, R.layout.kodi_last_show_panel, _context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    JSONArray movies()  {return mMovieDataList;}

    //-----------------------------------------------------------------------------------------------------------------
    JSONArray tvShows() {return mTvShowDataList;}

    //-----------------------------------------------------------------------------------------------------------------
    public void loadTvShows(){
        Thread queryShowsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Fill list with series
                mTvShowDataList = commandQueryTvShows();
                // 666 Update data in persistence.
                try{
                    JSONObject state = new JSONObject();
                    state.put("state", mTvShowDataList);
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

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private JSONArray mMovieDataList;
    private JSONArray mTvShowDataList;

}

