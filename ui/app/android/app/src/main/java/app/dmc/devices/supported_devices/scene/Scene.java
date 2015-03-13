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
import android.util.Pair;

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
    public List<Pair<String,Boolean>> panelTypes(){
        List<Pair<String,Boolean>> types = new ArrayList<>();
        types.add(new Pair<>(PANEL_TYPE_SCENE, true));
        return types;
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Override
    public DevicePanel createPanel(String _type, Context _context) {
        if(_type.equals(PANEL_TYPE_SCENE)) {
            return new ScenePanel(this, mPanelData, mChildActions, R.layout.scene_layout, _context);
        }
        return null;
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
			base.put("type", "Scene");
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

    protected static String PANEL_TYPE_SCENE = "Scene";
}
