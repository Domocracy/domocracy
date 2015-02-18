///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.List;

import app.dmc.Room;

public class RoomSelector {
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public RoomSelector(final Context _context, final List<Room>_rooms){
        // Store Rooms
        mRooms = _rooms;

        // Init Selector
        mSelector = new ViewFlipper(_context);

        // Testing Room images
        View roomView1 = mRooms.get(0).view(_context);
        mSelector.addView(roomView1);
        Bitmap snapshot = Bitmap.createBitmap(roomView1.getWidth(), roomView1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(snapshot);
        roomView1.draw(c);
        ImageView iv = new ImageView(_context);
        iv.setImageBitmap(snapshot);

        /*mSelector.addView(iv);
        mSelector.addView(mRooms.get(1).view(_context));*/


        /*layout.setOnTouchListener(new View.OnTouchListener() {
            Bitmap mSnapshot;
            @Override
            public boolean onTouch(View _view, MotionEvent _event) {
                switch (_event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    // Get a snapshot of the current room
                    mSnapshot = Bitmap.createBitmap(_view.getWidth(), _view.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas c = new Canvas(mSnapshot);
                    _view.draw(c);

                    // Clean layout
                    layout.removeAllViews();

                    ImageView iv = new ImageView(_view.getContext());
                    iv.setImageBitmap(mSnapshot);
                    layout.addView(iv);
                    View room = mRooms.get(0).view(_context);
                    layout.addView(room);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                }

                layout.removeView(iv);

                return false;
            }
        });*/
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mSelector;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private List<Room>      mRooms = null;
    private ViewFlipper     mSelector = null;

}
