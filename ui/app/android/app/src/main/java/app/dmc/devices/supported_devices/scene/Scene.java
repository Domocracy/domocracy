///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Domocracy Android App
//         Author: Pablo R.S.
//         Date:    2015-FEB-19
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//


package app.dmc.devices.supported_devices.scene;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dmc.R;
import app.dmc.core.Persistence;
import app.dmc.devices.Device;
import app.dmc.devices.DevicePanel;

public class Scene extends Device {
    //-----------------------------------------------------------------------------------------------------------------
    //  Public Interface
    public Scene(JSONObject _sceneData) {
        super(_sceneData);

        try {
            mChildActions = _sceneData.getJSONArray("children");
			mPanelData = _sceneData.getJSONArray("panels");
        } catch (JSONException _jsonException) {
            _jsonException.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public List<String> panelTypes(){
        List<String> types = new ArrayList<>();
        types.add("Complete");
        return types;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        if(_type.equals("Complete")) {
            return new ScenePanel(this, mPanelData, mChildActions, R.layout.scene_layout, _context);
        }
        return null;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public JSONObject action(JSONObject _stateInfo) {
		JSONObject command = new JSONObject();
		try {
			command.put("method", "PUT");
			command.put("cmd", new JSONObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return command;
    }

	//-----------------------------------------------------------------------------------------------------------------
	public void saveModifications(JSONArray _newActions) {
		mChildActions = _newActions;
		save();
	}

	//-----------------------------------------------------------------------------------------------------------------
	@Override
	protected JSONObject serialize() {
		JSONObject  base = super.serialize();
		try {
			base.put("children", mChildActions);
			base.put("panels", mPanelData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base;
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void save() {
		Persistence.get().putJSON(id(), serialize());
	}

    //-----------------------------------------------------------------------------------------------------------------
    // Private Members
    private JSONArray mChildActions;
	private JSONArray mPanelData;
}
