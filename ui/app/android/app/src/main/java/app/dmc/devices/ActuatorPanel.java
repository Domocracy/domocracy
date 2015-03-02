///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

package app.dmc.devices;


import android.content.Context;

import org.json.JSONObject;

public abstract class ActuatorPanel extends DevicePanel{
    //-----------------------------------------------------------------------------------------------------------------
    public ActuatorPanel(Actuator _parentActuator, JSONObject _panelData, int _layoutResId, Context _context){
        super(_parentActuator, _layoutResId, _context);
        mParentActuator = _parentActuator;
    }
    //-----------------------------------------------------------------------------------------------------------------
    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsPaused = false;
        Log.d("DOMOCRACY", "Intercepted Touch event. X: " + ev.getX() + "; Y: " + ev.getY());
        return false;
    }*/

    //-----------------------------------------------------------------------------------------------------------------
    public void pause(){
        mIsPaused = true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject action(){
        return mParentActuator.action(null);
    }

    //-----------------------------------------------------------------------------------------------------------------
    protected Actuator mParentActuator;
    boolean mIsPaused = false;
}
