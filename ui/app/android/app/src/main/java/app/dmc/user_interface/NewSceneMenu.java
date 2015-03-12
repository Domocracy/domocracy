///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-MAR-12
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.user_interface;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Pair;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.dmc.User;

public class NewSceneMenu{
    public NewSceneMenu(Context _context){
        mMenuBuilder = new AlertDialog.Builder(_context);

        createDialog(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDialog(Context _context) {
        // Load devices and put them into the list
        setContentView(_context);
        // Buttons
        mMenuBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Intentionally blank
            }
        });
        mMenuBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addScene();
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

        mDevList = new ListView(_context);
        List<String> deviceIds = User.get().getCurrentHub().deviceIds();
        layout.addView(new DevicesCheckList(_context, deviceIds));

        mMenuBuilder.setView(layout);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void addScene(){

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    ListView mDevList;

    //-----------------------------------------------------------------------------------------------------------------
    // Inner classes
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
                listData.add(panelsChecked.panelsChecked());
            }

            return listData;
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private members
        private List<String>                    mDeviceIds;
        private List<PanelsCheckList>           mDevicePanelsList;
    }
}
