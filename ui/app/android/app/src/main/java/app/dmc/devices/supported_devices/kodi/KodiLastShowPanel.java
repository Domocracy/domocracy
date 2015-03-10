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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.R;
import app.dmc.devices.Actuator;
import app.dmc.devices.ActuatorPanel;


public class KodiLastShowPanel extends ActuatorPanel {
    //-----------------------------------------------------------------------------------------------------------------
    public KodiLastShowPanel(final Actuator _parentActuator, final JSONObject _panelData, int _layoutResId, Context _context) {
        super(_parentActuator, _panelData, _layoutResId, _context);

        List<String> loadingDummyList = new ArrayList<String>();
        loadingDummyList.add("Loading Tv Shows");
        mSpinnerAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, loadingDummyList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTvShowSelector = (Spinner) findViewById(R.id.tvShowSelector);
        mTvShowSelector.setAdapter(mSpinnerAdapter);

        setUpView();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private interface
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

        JSONObject response = mParentActuator.runCommand(request);
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
    // View set up methods
    private void setUpView(){
        setUpClickAction();
        setUpTvShowSelector();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setUpClickAction(){
        // Set click action to the panel.
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread commThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Put dev in "Sending mode"
                        // Send Response
                        int tvShowIndex = mTvShowSelector.getSelectedItemPosition();
                        JSONObject request = new JSONObject();
                        try {
                            request.put("method", "PUT");

                            JSONObject cmd = new JSONObject();
                            cmd.put("cmd", "lastEpisode");
                            JSONObject tvshow = mTvShowList.get(tvShowIndex);
                            cmd.put("tvshowid", tvshow.getInt("tvshowid"));

                            request.put("cmd", cmd);
                        } catch (JSONException _jsonException){
                            _jsonException.printStackTrace();
                        } catch (IndexOutOfBoundsException _indexOutOfBoundException){
                            Log.d("DOMOCRACY", "There arent any tv show in the list");
                        }
                        JSONObject response = mParentActuator.runCommand(request);
                        // if(response OK){
                        //      Dev in mode OK
                        //else
                        //      Dev back to last state

                    }
                });
                commThread.start();
            }
        });

    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setUpTvShowSelector(){
        Thread queryShowsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Fill list with series
                final List<String> tvShowsList = new ArrayList<>();
                JSONArray jsonShowList = commandQueryTvShows();
                try{
                    if(jsonShowList.length() == 0){
                        tvShowsList.add("KODI hasn't got TV shows");
                    }
                    for(int i = 0; i < jsonShowList.length(); i++){
                        JSONObject tvshow = jsonShowList.getJSONObject(i);
                        mTvShowList.add(tvshow);
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
        });
        //queryShowsThread.start(); --- > Move to device not panel
    }

    private List<JSONObject> mTvShowList = new ArrayList<>();
    private Spinner mTvShowSelector;
    ArrayAdapter<String> mSpinnerAdapter;
}