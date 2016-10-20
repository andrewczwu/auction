package test.endtoend.auctionsniper;

//import static auctionsniper.ui.MainWindow.NEW_ITEM_ID_NAME;
//import static auctionsniper.ui.MainWindow.NEW_ITEM_STOP_PRICE_NAME;
//import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

import auctionsniper.ui.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import org.hamcrest.Matchers;

public class AuctionSniperDriver extends JFrameDriver {
	 public AuctionSniperDriver(int timeoutMillis) { 
		    super(new GesturePerformer(), 
		          JFrameDriver.topLevelFrame( 
		            named(MainWindow.MAIN_WINDOW_NAME), 
		            showingOnScreen()), 
		            new AWTEventQueueProber(timeoutMillis, 100)); 
		  }
	 
	public void showsSniperStatus(String statusText) {
		 new JLabelDriver(
				 this, named(MainWindow.SNIPER_STATUS_NAME)).hasText(org.hamcrest.Matchers.equalTo(statusText));
	 }
}
