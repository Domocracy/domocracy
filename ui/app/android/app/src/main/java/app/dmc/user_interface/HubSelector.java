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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import app.dmc.R;
import app.dmc.User;

public class HubSelector implements AdapterView.OnItemSelectedListener {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    HubSelector(Context _context){
        mHubList = User.get().getHubsIds();

        mHubSpinner = new Spinner(_context);
        ArrayAdapter<String> adapterHubList = new ArrayAdapter<String>(_context,R.layout.support_simple_spinner_dropdown_item,mHubList);
        mHubSpinner.setAdapter(adapterHubList);
        mHubSpinner.setOnItemSelectedListener(this);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //here is where we need to implement the hub selection.
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //-----------------------------------------------------------------------------------------------------------------
    View view(){
        return mHubSpinner;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //  Private Interface
    private class HubViewHolder{
        TextView mName;
    }

    // Members
    private List<String>    mHubList;
    private Spinner         mHubSpinner;
}
