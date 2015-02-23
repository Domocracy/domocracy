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
import android.view.MotionEvent;

import org.json.JSONObject;

public abstract class ActuatorPanel extends DevicePanel{
    //-----------------------------------------------------------------------------------------------------------------
    public ActuatorPanel(Actuator _parentActuator, JSONObject _panelData, Context _context){
        super(_parentActuator, _context);
        mParentActuator = _parentActuator;
    }
    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsPaused = false;
        return false;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void pause(){
        mIsPaused = true;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public JSONObject action(){
        return mParentActuator.action(null);
    }

    //-----------------------------------------------------------------------------------------------------------------
    Actuator mParentActuator;
    boolean mIsPaused = false;
}
