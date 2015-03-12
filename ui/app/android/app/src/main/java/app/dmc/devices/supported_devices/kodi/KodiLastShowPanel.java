///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices.supported_devices.kodi;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.R;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;


public class KodiLastShowPanel extends DevicePanel {
    //-----------------------------------------------------------------------------------------------------------------
    public KodiLastShowPanel(final Device _parentActuator, int _layoutResId, Context _context) {
        super(_parentActuator, _layoutResId, _context);

        init(_context);
        setCallbacks();
    }

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	public JSONObject queryCommand() {
		int tvShowIndex = mTvShowSelector.getSelectedItemPosition();
		JSONObject request = new JSONObject();
		try {
			request.put("method", "PUT");

			JSONObject cmd = new JSONObject();
			cmd.put("cmd", "lastEpisode");
			JSONObject tvshow = mTvShowList.getJSONObject(tvShowIndex);
			cmd.put("tvshowid", tvshow.getInt("tvshowid"));

			request.put("cmd", cmd);
		} catch (JSONException _jsonException) {
			_jsonException.printStackTrace();
		} catch (IndexOutOfBoundsException _indexOutOfBoundException) {
			Log.d("DOMOCRACY", "There arent any tv show in the list");
		}
		return request;
	}

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {
        // Fill list with series
        final List<String> tvShowsList = new ArrayList<>();
        try{
            mTvShowList = new JSONArray();
            JSONArray jsonShowList = _state.getJSONArray("tvshows");
            if(jsonShowList.length() == 0){
                tvShowsList.add("KODI hasn't got TV shows");
            }
            for(int i = 0; i < jsonShowList.length(); i++){
                JSONObject tvshow = jsonShowList.getJSONObject(i);
                mTvShowList.put(tvshow);
                tvShowsList.add(tvshow.getString("label"));
            }

            mTvShowSelector.post(new Runnable() {
                @Override
                public void run() {
                    mSpinnerAdapter.clear();
                    for(String label: tvShowsList) {
                        mSpinnerAdapter.add(label);
                    }
                    mSpinnerAdapter.notifyDataSetChanged();
                }
            });

        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }
        Log.d("DOMOCRACY", "List of Tv-shows");
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private interface
    private void init(Context _context){
        mTvShowList = ((Kodi) device()).tvShows();
        List<String> loadingDummyList = new ArrayList<>();
        for(int i = 0; i < mTvShowList.length(); i++){
            try {
                loadingDummyList.add(mTvShowList.getJSONObject(i).getString("label"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mSpinnerAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, loadingDummyList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTvShowSelector = (Spinner) findViewById(R.id.tvShowSelector);
        mTvShowSelector.setAdapter(mSpinnerAdapter);
    }
    //-----------------------------------------------------------------------------------------------------------------
    // View set up methods
    private void setCallbacks(){
       extendCallback();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void extendCallback(){
        // Update tvshowlist
        mTvShowSelector.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((Kodi) device()).loadTvShows();
                return false;
            }
        });
    }

    // Private members
    private Spinner mTvShowSelector;
    private ArrayAdapter<String> mSpinnerAdapter;

    private JSONArray mTvShowList;
}