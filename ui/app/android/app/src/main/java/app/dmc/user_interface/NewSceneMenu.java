///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-MAR-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.user_interface;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.Pair;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.Hub;
import app.dmc.User;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class NewSceneMenu{
    public NewSceneMenu(Activity _activity){
        mMenuBuilder = new AlertDialog.Builder(_activity);

        createDialog(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDialog(final Activity _activity) {
        // Load devices and put them into the list
        setContentView(_activity);
        // Buttons
        mMenuBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Intentionally blank
            }
        });
        mMenuBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addScene(_activity);
            }
        });

        mMenuBuilder.create().show();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void setContentView(Context _context){
        LinearLayout layout = new LinearLayout(_context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(_context);
        title.setText("Add new scene");
        layout.addView(title);

        List<String> deviceIds = User.get().getCurrentHub().deviceIds();
        mCheckList = new DevicesCheckList(_context, deviceIds);
        layout.addView(mCheckList);

        mMenuBuilder.setView(layout);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private JSONObject gatherSceneInfo(){
        List<Pair<String, List<String>>>  devList = mCheckList.panelsChecked();
        if(devList.size() == 0)
            return null;

        JSONObject sceneJSON = new JSONObject();
        try{
            sceneJSON.put("type", "Scene");
            sceneJSON.put("name", "New Scene");
            sceneJSON.put("hub", User.get().getCurrentHub().name());

            JSONArray panels = new JSONArray();
            for(Pair<String, List<String>> dev: devList){
                for(String panel: dev.second){
                    JSONObject panelJSON = new JSONObject();
                    panelJSON.put("devId", dev.first);
                    panelJSON.put("type", panel);
                    panels.put(panelJSON);
                }
            }
            sceneJSON.put("panels", panels);
            sceneJSON.put("children", new JSONArray());
            sceneJSON.put("panelType", "Scene");
        }catch (JSONException _jsonException){
            _jsonException.printStackTrace();
            return null;
        }
        return sceneJSON;
    }
    //-----------------------------------------------------------------------------------------------------------------
    private void addScene(final Activity _activity){
        Thread comThread = new Thread(){
            @Override
            public void run() {
                // Serialize info into a JSON
                JSONObject sceneJSON = gatherSceneInfo();

                // Send info to hub
                Hub hub = User.get().getCurrentHub();
                JSONObject response = hub.send("/addDevice", sceneJSON);
                try{
                    Log.d("Response", response.toString());
                    if (response.getString("result").equals("ok")){
                        sceneJSON.put("id", response.getString("id"));
                    }
                }catch (JSONException _jsonException) {
                    Log.d("Response", "Malformed response");
                    return;
                }catch (NullPointerException _nullPtrException){
                    Log.d("Response", "Can't connect to Server");
                    return;
                }

                // Check response, if OK add device
                Device dev = User.get().addNewDevice(sceneJSON);
                final DevicePanel panel = dev.createPanel("Scene", _activity);
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Hub hub = User.get().getCurrentHub();
                        hub.room(hub.currentRoom()).addPanel(panel);
                    }
                });
            }
        };
        comThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    private DevicesCheckList    mCheckList;


    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    // Inner classes
    //-----------------------------------------------------------------------------------------------------------------
    class PanelsCheckList extends  LinearLayout{
        //-------------------------------------------------------------------------------------------------------------
        public PanelsCheckList(Context _context, String _id){
            super(_context);
            setOrientation(VERTICAL);

            mCheckBoxList = new ArrayList<>();
            mDevId = _id;
            mPanelTypes = User.get().getCurrentHub().device(_id).panelTypes();

            buildView(_context);
        }

        //-------------------------------------------------------------------------------------------------------------
        public Pair<String, List<String>> panelsChecked(){
            List<String> panels = new ArrayList<>();
            for(int i = 0 ; i < mCheckBoxList.size(); i++){
                if(mCheckBoxList.get(i).isChecked()){
                    panels.add(mPanelTypes.get(i).first);
                }
            }
            if(panels.size() == 0)
                return null;

            Pair<String, List<String>> info = new Pair<>(mDevId, panels);
            return info;
        }

        //-------------------------------------------------------------------------------------------------------------
        // Private methods
        private void buildView(Context _context){
            TextView devName = new TextView(_context);
            devName.setText(User.get().getCurrentHub().device(mDevId).name());
            addView(devName);

            for(int i = 0; i < mPanelTypes.size(); i++){
                if(mPanelTypes.get(i).second){
                    CheckBox panelCheckBox = new CheckBox(_context);
                    panelCheckBox.setText(mPanelTypes.get(i).first);
                    mCheckBoxList.add(panelCheckBox);
                    addView(panelCheckBox);
                }
            }

        }

        //-------------------------------------------------------------------------------------------------------------
        // Private Members
        String                      mDevId;
        List<Pair<String, Boolean>> mPanelTypes;

        List<CheckBox>              mCheckBoxList;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    class DevicesCheckList extends ScrollView{
        DevicesCheckList(Context _context, List<String> _deviceIds){
            super(_context);
            mDevicePanelsList   = new ArrayList<>();
            mDeviceIds          = _deviceIds;

            buildView(_context);
        }
        //-----------------------------------------------------------------------------------------------------------------
        public void buildView(Context _context){
            LinearLayout base = new LinearLayout(_context);
            base.setOrientation(LinearLayout.VERTICAL);
            for(int i = 0; i < mDeviceIds.size(); i++){
                PanelsCheckList list = new PanelsCheckList(_context, mDeviceIds.get(i));
                mDevicePanelsList.add(list);
                base.addView(list);
            }

            addView(base);
        }

        //-----------------------------------------------------------------------------------------------------------------
        public List<Pair<String, List<String>>> panelsChecked(){
            List<Pair<String, List<String>>> listData = new ArrayList<>();
            for(PanelsCheckList panelsChecked: mDevicePanelsList){
                Pair<String, List<String>> data = panelsChecked.panelsChecked();
                if(data != null)
                    listData.add(data);
            }

            return listData;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private members
        private List<String>                    mDeviceIds;
        private List<PanelsCheckList>           mDevicePanelsList;
    }
}
