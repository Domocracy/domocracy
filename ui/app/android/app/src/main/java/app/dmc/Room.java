///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.devices.DevicePanel;
import app.dmc.user_interface.PanelList;
import app.dmc.user_interface.RoomHeader;

public class Room {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public Room(JSONObject _data, Hub _hub, Context _context){
        mLayout = new ScrollView(_context);

        mDefaultHub = _hub;
        try{
            mId         = _data.getString("roomId");
            mName       = _data.getString("name");
            mPanels     = new PanelList(_data.getJSONArray("panels"), _hub, _context);
            mHeader     = new RoomHeader(_context);
        }catch(JSONException e){
            e.printStackTrace();
        }

        // ScrollView can host only one child
        LinearLayout baseLayout = new LinearLayout(_context);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        baseLayout.addView(mHeader);
        baseLayout.addView(mPanels);
        mLayout.addView(baseLayout);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mLayout;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void addPanel(DevicePanel _panel){
        mPanels.addPanel(_panel);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Getters
    public String name(){
        return mName;
    }
    public String id(){ return mId; }

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    // Identification
    private String mName;
    private String mId;

    private Hub mDefaultHub;

    private ScrollView  mLayout;
    private PanelList   mPanels;
    private RoomHeader  mHeader;
}
