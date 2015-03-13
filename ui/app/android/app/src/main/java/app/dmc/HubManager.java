package app.dmc;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Joscormir on 13/02/2015.
 */

public class HubManager {

    //-----------------------------------------------------------------------------------------------------------------
    static public void init(Context _context){
        sInstance =  new HubManager(_context);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public static HubManager get(){
        return sInstance;
    }

	//-----------------------------------------------------------------------------------------------------------------
	public static void end() {
		sInstance.onEnd();
		sInstance = null;
	}

    //-----------------------------------------------------------------------------------------------------------------
    public Hub hub(String _id){
        Hub hub = mHubMap.get(_id);
		if(hub == null) {
			hub = new Hub(_id, mContext);
			mHubMap.put(_id, hub);
		}
        return hub;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Private Interface.
    private HubManager(Context _context){
		mContext = _context;
		mHubMap  = new HashMap<String,Hub>();
	}

	//-----------------------------------------------------------------------------------------------------------------
	private void onEnd() {
		for( Map.Entry<String, Hub> hub : mHubMap.entrySet()) {
			hub.getValue().save();
		}
		mHubMap = null;
	}

    //-----------------------------------------------------------------------------------------------------------------
    private Map<String,Hub> mHubMap;
	private Context			mContext;

    //-----------------------------------------------------------------------------------------------------------------
    private static HubManager sInstance = null;

}
