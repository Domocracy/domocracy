///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Carmelo J. Fdez-Agüera & Pablo R.S.
//         Date:    2015-FEB-23
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package app.dmc.devices;

import android.view.View;

import org.json.JSONObject;

public abstract class DevicePanel {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev){
        mParentDevice = _dev;
    }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract View view();
    public abstract void stateChanged(JSONObject _state);

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    Device mParentDevice;
}
