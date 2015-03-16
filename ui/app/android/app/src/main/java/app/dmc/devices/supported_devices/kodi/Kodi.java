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
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

		//loadTvShows();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public List<Pair<String,Boolean>> panelTypes(){
        List<Pair<String,Boolean>> types = new ArrayList<>();
        types.add(new Pair<>(PANEL_TYPE_LAST_SHOW, true));
        return types;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        if(_type.equals(PANEL_TYPE_LAST_SHOW)) {
            return new KodiLastShowPanel(this, R.layout.kodi_last_show_panel, _context);
        }
        return null;
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
                try{
                    JSONObject state = new JSONObject();
                    state.put("tvshows", mTvShowDataList);
                    notifyPanels(state); // Update panels
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
            }
        });
        queryShowsThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
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
	@Override
	public JSONObject serialize() {
		JSONObject serial = super.serialize(); // Retrieve base Device's info
		JSONObject media = new JSONObject();
		try {
			media.put("tvshows", mTvShowDataList);
			media.put("movies", mMovieDataList);
			serial.put("media", media);
			serial.put("type", "Kodi");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private JSONArray mMovieDataList;
    private JSONArray mTvShowDataList;

    private static String PANEL_TYPE_LAST_SHOW = "LastShow";
}

