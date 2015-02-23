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
import android.view.ViewGroup;

import org.json.JSONObject;

public abstract class DevicePanel extends ViewGroup {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, Context _context){
        super(_context);
        mParentDevice = _dev;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract void stateChanged(JSONObject _state);

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    Device mParentDevice;
}
