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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import app.dmc.user_interface.PanelList;

public class Room extends BaseAdapter {
    //-----------------------------------------------------------------------------------------------------------------
    // Public Interface
    public Room(JSONObject _data, Hub _hub, Context _context){
        mLayout = new ListView(_context);
        mLayout.setAdapter(this);

        mDefaultHub = _hub;

        try{
            mId         = _data.getString("id");
            mName       = _data.getString("name");
            mPanels = new PanelList(_data.getJSONArray("panels"), _hub, _context);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mLayout;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Adapter methods
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int _position) {
        if(_position == 0)
            return null;    // Header
        if(_position == 1)
            return mPanels;    // PanelList

        assert false;
        return null;
    }

    @Override
    public long getItemId(int _position) {
        return 0;   // Unused.
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        if(_position == 0)
            return null;    // Header
        if(_position == 1)
            return mPanels;    // PanelList

        assert false;
        return null;
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

    private ListView    mLayout;
    private PanelList   mPanels;

}
