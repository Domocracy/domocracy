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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import app.dmc.Room;

public class RoomSelector {
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public RoomSelector(final Context _context, final List<Room>_rooms){
        mRooms = _rooms;
        layout = new LinearLayout(_context);

        layout.addView(mRooms.get(0).view(_context));

        layout.setOnTouchListener(new View.OnTouchListener() {
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



                /*TranslateAnimation roomOutLeft = new TranslateAnimation(    Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, -1,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
                roomOutLeft.setDuration(5000);
                iv.setAnimation(roomOutLeft);

                TranslateAnimation roomInRight = new TranslateAnimation(    Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 1,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
                roomInRight.setDuration(5000);
                room.setAnimation(roomInRight);*/

                layout.removeView(iv);

                return false;
            }
        });

        /*Bitmap b = Bitmap.createBitmap(mView.getWidth() , mView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                mView.draw(c);

                mView = mRooms.get(1).view(_context);

                TranslateAnimation roomOutLeft = new TranslateAnimation(    Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);

                TranslateAnimation roomInRight = new TranslateAnimation(    Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0);
            }
            */

    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return layout;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    List<Room>              mRooms = null;
    private LinearLayout    layout = null;

}
