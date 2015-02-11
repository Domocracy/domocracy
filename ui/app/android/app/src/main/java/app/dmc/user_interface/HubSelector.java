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
import android.widget.Spinner;

import java.util.List;

import app.dmc.Hub;

public class HubSelector extends BaseAdapter{
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    HubSelector(Context _context, List<Hub> _hubList){
        mHubList = _hubList;

        mHubSpinner = new Spinner(_context);
        mHubSpinner.setAdapter(this);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Spinner adapter Interface
    @Override
    public int getCount() {
        return 0;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public Object getItem(int position) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private List<Hub> mHubList;
    private Spinner mHubSpinner;
}
