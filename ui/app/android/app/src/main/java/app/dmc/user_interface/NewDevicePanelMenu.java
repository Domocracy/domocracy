package app.dmc.user_interface;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-MAR-30
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

import android.app.AlertDialog;
import android.content.Context;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.dmc.Room;
import app.dmc.User;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class NewDevicePanelMenu {
    public NewDevicePanelMenu(Context _context, Room _room){
        mMenuBuilder = new AlertDialog.Builder(_context);
        mParentRoom = _room;

        createDialog(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void createDialog(Context _context) {
        // Load devices and put them into the list
        setContentView(_context);
        mMenuBuilder.setTitle("Choose device to be added:");

        mDeviceDialog = mMenuBuilder.create();
        mDeviceDialog.show();
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void setContentView(final Context _context){
        LinearLayout layout = new LinearLayout(_context);
        layout.setOrientation(LinearLayout.VERTICAL);
        List<String> devices = User.get().getCurrentHub().deviceIds();
        for(final String id :devices){
            TextView tv = new TextView(_context);
            tv.setText(User.get().getCurrentHub().device(id).name());
            tv.setTextSize(30);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Device dev = User.get().getCurrentHub().device(id);
                    List<Pair<String, Boolean>> types = dev.panelTypes();
                    if(types.size() == 1){
                        mParentRoom.addPanel(dev.newPanel(types.get(0).first, _context));
                        mDeviceDialog.dismiss();
                    }else{
                        PanelListMenu menu = new PanelListMenu(_context, mParentRoom, dev);
                    }
                }
            });
            layout.addView(tv);
        }
        mMenuBuilder.setView(layout);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    private Room                mParentRoom;
    private AlertDialog         mDeviceDialog;


    //-----------------------------------------------------------------------------------------------------------------
    // Inner classes
    class PanelListMenu{
        public PanelListMenu(Context _context, Room _room, Device _device){
            mMenuBuilder = new AlertDialog.Builder(_context);
            mParentRoom = _room;
            mDevice = _device;
            mPanels =  new ArrayList<>();

            createDialog(_context);
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private methods
        private void createDialog(Context _context) {
            // Load devices and put them into the list
            setContentView(_context);
            mMenuBuilder.setTitle("Choose controller to be added:");

            mPanelDialog = mMenuBuilder.create();
            mPanelDialog.show();

        }

        //-----------------------------------------------------------------------------------------------------------------
        private void setContentView(final Context _context){
            List<Pair<String, Boolean>> types = mDevice.panelTypes();
            LinearLayout layout = new LinearLayout(_context);
            layout.setOrientation(LinearLayout.VERTICAL);
            for(int i = 0; i < types.size(); i++){
                final String type = types.get(i).first;
                DevicePanel panel = User.get().getCurrentHub().device(mDevice.id()).newPanel(type, _context);
                panel.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            mParentRoom.addPanel(User.get().getCurrentHub().device(mDevice.id()).newPanel(type, _context));
                            mPanelDialog.dismiss();
                            mDeviceDialog.dismiss();
                        }
                        return true;
                    }
                });
                mPanels.add(panel);
                layout.addView(panel);
            }
            mMenuBuilder.setView(layout);
        }

        //-----------------------------------------------------------------------------------------------------------------
        // Private members
        private AlertDialog.Builder mMenuBuilder;
        private Room                mParentRoom;
        private Device              mDevice;
        private ArrayList<DevicePanel> mPanels;
        private AlertDialog         mPanelDialog;
    }

}