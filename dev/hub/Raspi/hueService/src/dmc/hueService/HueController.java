///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Domocracy: Hue Service
//			Author:	Pablo R.S.
//			Date:	2015-MAR-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



package dmc.hueService;

import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

public class HueController {
	//---------------------------------------------------------------------------------------------------------------------
	// Static interface	
	public static HueController get(){
		if(mInstance == null)
			init();
		
		return mInstance;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	private static void init(){
		mInstance = new HueController();
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Public interface
	public void searchBridges(){
		PHBridgeSearchManager sm = (PHBridgeSearchManager) mHueSdkInstance.getSDKService(PHHueSDK.SEARCH_BRIDGE);
	    sm.search(true, true); 
	    
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	public List<String> bridgesIps(){
		List<String> ipList = new ArrayList<>();
		for(PHAccessPoint accessPoint: mLastAccessPoints){
			ipList.add(accessPoint.getIpAddress());
		}
		return ipList;
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Private methods
	private HueController(){
		mHueSdkInstance = PHHueSDK.getInstance();
		mHueSdkInstance.getNotificationManager().registerSDKListener(new DmcHueBridgeListener());
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Private members
	private static HueController mInstance = null;
	
	private PHHueSDK			mHueSdkInstance;
	private	List<PHBridge>		mConnectedBridges;
	private List<PHAccessPoint>	mLastAccessPoints;
	
	//---------------------------------------------------------------------------------------------------------------------
	// Inner classes
	class DmcHueBridgeListener implements PHSDKListener{
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onAccessPointsFound(List<PHAccessPoint> _accessPoints) {
			mLastAccessPoints = _accessPoints;
			
			for(int i = 0; i < mLastAccessPoints.size(); i++){
				PHAccessPoint accessPoint = mLastAccessPoints.get(i);
				accessPoint.setUsername("Bardo91");
			}
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onAuthenticationRequired(PHAccessPoint _accessPoint) {
			mHueSdkInstance.startPushlinkAuthentication(_accessPoint);
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onBridgeConnected(PHBridge _bridge) {
			mConnectedBridges.add(_bridge);
			
			mHueSdkInstance.setSelectedBridge(_bridge);
			mHueSdkInstance.enableHeartbeat(_bridge, PHHueSDK.HB_INTERVAL);
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onCacheUpdated(List<Integer> _cache, PHBridge _bridge) {
			if (_cache.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
            }
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onConnectionLost(PHAccessPoint _accessPoint) {
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onConnectionResumed(PHBridge _bridge) {
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onError(int _code, String _message) {
		}
		
		//---------------------------------------------------------------------------------------------------------------------
		@Override
		public void onParsingErrors(List<PHHueParsingError> _errorList) {
		}
		
	}
	
}
