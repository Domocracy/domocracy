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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import app.dmc.R;

public class RoomHeader extends LinearLayout{
    //-----------------------------------------------------------------------------------------------------------------
    public RoomHeader(Context _context){
        super(_context);

        LayoutInflater inflater = LayoutInflater.from(_context);
        addView(inflater.inflate(R.layout.room_header, null));

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", "Someone is touching the header of the room");
                return false;
            }
        });

        }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
}
