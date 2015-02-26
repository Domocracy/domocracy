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

        final Spinner tvShowSelector = (Spinner) findViewById(R.id.tvShowSelector);
        // Fill with series on startup
        Thread queryShowsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> tvShowsList = new ArrayList<>();
                // Fill list with series

                JSONArray jsonShowList = queryTvShows();

                try{
                    // Dummy Load
                    JSONObject ej1 = new JSONObject();
                    ej1.put("label", "Matrix Reload");
                    JSONObject ej2 = new JSONObject();
                    ej2.put("label", "SharkNado");
                    jsonShowList.put(ej1);
                    jsonShowList.put(ej2);
                    //

                    for(int i = 0; i < jsonShowList.length(); i++){
                        tvShowsList.add(jsonShowList.getJSONObject(i).getString("label"));
                    }
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tvShowsList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tvShowSelector.setAdapter(spinnerAdapter);

            }
        });
        queryShowsThread.start();

    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void stateChanged(JSONObject _state) {

    }



    //-----------------------------------------------------------------------------------------------------------------
    // Private interface
    JSONArray queryTvShows(){
        JSONObject command = new JSONObject();
        try{
            command.put("cmd","tvshows");
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
        }

        JSONObject response = mParentActuator.runCommand(command);

        JSONArray jsonShowList;
        try{
            jsonShowList = response.getJSONArray("tvshows");

        } catch (JSONException _jsonException){
            _jsonException.printStackTrace();
            return null;
        }
        return jsonShowList;
    }
}