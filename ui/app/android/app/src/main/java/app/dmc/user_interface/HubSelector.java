///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-10
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import app.dmc.Hub;
import app.dmc.HubManager;
import app.dmc.R;
import app.dmc.User;

public class HubSelector extends BaseAdapter{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    HubSelector(Context _context){
        mHubList = User.get().getHubsIds();

        mHubSpinner = new Spinner(_context);
        ArrayAdapter<String> adapterHubList = new ArrayAdapter<String>(_context,R.layout.support_simple_spinner_dropdown_item,mHubList);
        mHubSpinner.setAdapter(adapterHubList);
    }

    View view(){
        return mHubSpinner;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Spinner adapter Interface
    @Override
    public int getCount() {
        return mHubList.size();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public Object getItem(int _position) {
        return HubManager.get().hub(mHubList.get(_position));
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public long getItemId(int _position) {
        return _position;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        HubViewHolder view;

        if(_convertView == null){
            Context context = _parent.getContext();

            LinearLayout ll = new LinearLayout(context);
            TextView tv = new TextView(context);
            ll.addView(tv);

            _convertView = ll;

            view = new HubViewHolder();
            view.mName = new TextView(context);
            view.mName.setText(((Hub)getItem(_position)).name());

            _convertView.setTag(view);

        }else{
            view = (HubViewHolder) _convertView.getTag();
        }

        return _convertView;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private class HubViewHolder{
        TextView mName;
    }

    // Members
    private List<String> mHubList;
    private Spinner mHubSpinner;
}
