///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//          Author: Pablo R.S.
//         Date:    2015-FEB-18
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.user_interface;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.List;

import app.dmc.R;
import app.dmc.Room;

public class RoomSelector {
    //-----------------------------------------------------------------------------------------------------------------
    // Public interface
    public RoomSelector(final Activity _activity, final List<Room>_rooms){
        // Store Rooms
        mRooms = _rooms;

        // Init Selector
        mSelector = new ViewFlipper(_activity);

        // Testing Room images
        View roomView1 = mRooms.get(mCurrentRoom).view();

        mSelector.addView(roomView1);

        mSelector.setOnTouchListener(new View.OnTouchListener() {
            final double OFFSET = 30;
            double iniX;
            ImageView snapShotView;

            @Override
            public boolean onTouch(View _view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        iniX = event.getX();
                        int width = _view.getWidth();
                        int height = _view.getHeight();

                        Bitmap snapshot = Bitmap.createBitmap(width, height , Bitmap.Config.ARGB_8888);
                        Canvas c = new Canvas(snapshot);
                        _view.draw(c);

                        snapShotView = new ImageView(_activity);
                        snapShotView.setImageBitmap(snapshot);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        double x = event.getX();
                        if((iniX - x) < - OFFSET){  // previous room
                            if(mCurrentRoom - 1 >= 0 ) {
                                mCurrentRoom--;

                                mSelector.removeAllViews();
                                mSelector.addView(snapShotView);
                                mSelector.addView(mRooms.get(mCurrentRoom).view());

                                mSelector.setInAnimation(_activity, R.anim.slide_in_left);
                                mSelector.setOutAnimation(_activity, R.anim.slide_out_right);
                                mSelector.showPrevious();

                            }

                        }else if((iniX - x) > OFFSET){  // next room
                            if(mCurrentRoom + 1 < mRooms.size()) {
                                mCurrentRoom++;

                                mSelector.removeAllViews();
                                mSelector.addView(snapShotView);
                                mSelector.addView(mRooms.get(mCurrentRoom).view());

                                mSelector.setInAnimation(_activity, R.anim.slide_in_right);
                                mSelector.setOutAnimation(_activity, R.anim.slide_out_left);
                                mSelector.showNext();

                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------
    public View view(){
        return mSelector;
    }
    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    private List<Room>      mRooms = null;
    private ViewFlipper     mSelector = null;
    private int             mCurrentRoom = 0;

}
