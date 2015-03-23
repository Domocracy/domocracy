///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-MAR-22
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.user_interface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.User;


public class NewRoomMenu {
    public NewRoomMenu(Activity _activity){
        mMenuBuilder = new AlertDialog.Builder(_activity);

        createDialog(_activity);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void createDialog(final Activity _activity) {
        // Load devices and put them into the list
        setContentView(_activity);
        mMenuBuilder.setTitle("Add new room");
        // Buttons
        mMenuBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Intentionally blank
            }
        });
        mMenuBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addRoom(_activity);
            }
        });

        mMenuBuilder.create().show();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private methods
    private void setContentView(Context _context){
        mRoomName = new EditText(_context);
        mRoomName.setText("New Room");

        mMenuBuilder.setView(mRoomName);
    }

    //-----------------------------------------------------------------------------------------------------------------
    private void addRoom(final Activity _activity){
        Thread comThread = new Thread(){
            @Override
            public void run() {
                JSONObject roomJSON  = new JSONObject();

                try{
                    roomJSON.put("name", mRoomName.getText());
                    roomJSON.put("panels", new JSONArray());
                    roomJSON.put("roomId", "baadfeed");
                }catch (JSONException _jsonException){
                    _jsonException.printStackTrace();
                }

                User.get().addRoom(roomJSON, _activity);
            }
        };
        comThread.start();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private AlertDialog.Builder mMenuBuilder;
    private EditText            mRoomName;
}
