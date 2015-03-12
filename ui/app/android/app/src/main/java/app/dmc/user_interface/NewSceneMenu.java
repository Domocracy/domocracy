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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.dmc.Hub;
import app.dmc.User;

public class NewSceneMenu{
    public NewSceneMenu(Context _context){
        mMenuBuilder = new AlertDialog.Builder(_context);

        createDialog(_context);
    }

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

        CheckList checkList = new CheckList(_context, deviceIds);
        layout.addView(checkList.view());

        mMenuBuilder.setView(layout);
    }

    private void addScene(){

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    ListView mDevList;

    //-----------------------------------------------------------------------------------------------------------------
    // Inner classes
    class CheckList extends BaseAdapter{
        CheckList(Context _context, List<String> _deviceIds){
            mCheckList = new ListView(_context);
            mCheckBoxes = new ArrayList<>();
            mDeviceIds = _deviceIds;

            Hub hub = User.get().getCurrentHub();
            for(int i = 0; i <_deviceIds.size(); i++){
                CheckBox checkBox = new CheckBox(_context);
                checkBox.setText(hub.device(_deviceIds.get(i)).name());
                mCheckBoxes.add(checkBox);
            }

            mCheckList.setAdapter(this);
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public int getCount() {
            return mCheckBoxes.size();
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public Object getItem(int _position) {
            return mCheckBoxes.get(_position);
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public long getItemId(int _position) {
            return _position;
        }

        //-----------------------------------------------------------------------------------------------------------------
        @Override
        public View getView(int _position, View _convertView, ViewGroup _parent) {
            CheckBox checkBox = null;
            if (_convertView == null) {
                _convertView = new CheckBox(_parent.getContext());

                checkBox = new CheckBox(_parent.getContext());
                checkBox.setText(mCheckBoxes.get(_position).getText());
                checkBox.setChecked (mCheckBoxes.get(_position).isChecked());

                _convertView.setTag(checkBox);
            }
            else {
                checkBox = (CheckBox) _convertView.getTag();
            }

            //checkBox.setText(mCheckBoxes.get(_position).getText());
            //checkBox.setChecked (mCheckBoxes.get(_position).isChecked());

            return _convertView;

        }

        //-----------------------------------------------------------------------------------------------------------------
        View view(){
            return mCheckList;
        }

        // Members
        private List<String>        mDeviceIds;
        private List<CheckBox>      mCheckBoxes;
        private ListView            mCheckList;
    }
}
