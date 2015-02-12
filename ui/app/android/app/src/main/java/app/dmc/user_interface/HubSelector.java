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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import app.dmc.Hub;

public class HubSelector extends BaseAdapter{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    HubSelector(Context _context, Map<String,Hub> _hubList){
        mHubList = _hubList;

        mHubSpinner = new Spinner(_context);
        mHubSpinner.setAdapter(this);
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
        return mHubList.get(_position);
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
    private Map<String,Hub> mHubList;
    private Spinner mHubSpinner;
}
