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
    ActuatorPanel(Actuator _parentActuator, Context _context){
        super(_parentActuator, _context);
        mParentActuator = _parentActuator;
    }

    //-----------------------------------------------------------------------------------------------------------------
    JSONObject action(){
        return mParentActuator.action(null);
    }

    //-----------------------------------------------------------------------------------------------------------------
    Actuator mParentActuator;

}
