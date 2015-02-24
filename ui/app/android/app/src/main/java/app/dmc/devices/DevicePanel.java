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
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONObject;

public abstract class DevicePanel extends LinearLayout {
    //-----------------------------------------------------------------------------------------------------------------
    public DevicePanel(Device _dev, int _layoutResId, Context _context){
        super(_context);
        mParentDevice = _dev;


        View.inflate(_context, _layoutResId, this);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public void destroy(){
        mParentDevice.unregisterPanel(this);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    //-----------------------------------------------------------------------------------------------------------------
    public abstract void stateChanged(JSONObject _state);

    //-----------------------------------------------------------------------------------------------------------------
    // Private members
    Device mParentDevice;
}
