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
            request.put("url","tvshows");
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
                        JSONObject response = mParentActuator.runCommand(new JSONObject());
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
    boolean hasTvShowList = false;

    private void setUpTvShowSelector(){
        Spinner tvShowSelector = (Spinner) findViewById(R.id.tvShowSelector);
        final List<String> tvShowsList = new ArrayList<>();
        // Fill with series on startup
        
        Thread queryShowsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Fill list with series
                JSONArray jsonShowList = commandQueryTvShows();
                try{
                    for(int i = 0; i < jsonShowList.length(); i++){
                        JSONObject tvshow = jsonShowList.getJSONObject(i);
                        tvShowsList.add(tvshow.getString("label"));
                    }
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }
                hasTvShowList = true;
                Log.d("DOMOCRACY", "List of Tv-shows");
            }
        });
        queryShowsThread.start();

        Log.d("DOMOCRACY", "Waiting TV-shows");
        while(!hasTvShowList){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d("DOMOCRACY", "Filling adapter and spinner");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tvShowsList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tvShowSelector.setAdapter(spinnerAdapter);
    }

}