///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RoomHeader extends LinearLayout{
    //-----------------------------------------------------------------------------------------------------------------
    public RoomHeader(Context _context){
        super(_context);

        TextView titleView = new TextView(_context);
        titleView.setText("HEADER");
        addView(titleView);
    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
}
