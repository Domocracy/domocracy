///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		Domocracy: Hue Service
//			Author:	Pablo R.S.
//			Date:	2015-MAR-25
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package dmc.hueService;

public class HueService {
	public static void main(String[] args) {
		mController = HueController.get();
		mController.searchBridges();
		
		mDmcLink = new DmcLink();
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Members
	static HueController 	mController = null;
	static DmcLink			mDmcLink = null;
}
