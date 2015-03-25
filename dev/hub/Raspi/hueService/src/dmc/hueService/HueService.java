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
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// methods
	private void init(){
		mController = HueController.get();
		mController.searchBridges();
	}
	
	//---------------------------------------------------------------------------------------------------------------------
	// Members
	HueController mController = null;
}
