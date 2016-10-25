package test.endtoend.auctionsniper;

import static org.hamcrest.Matchers.containsString;
import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

import java.io.IOException;

import javax.swing.SwingUtilities;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;


public class ApplicationRunner { 
  public static final String SNIPER_ID = "sniper"; 
  public static final String SNIPER_PASSWORD = "sniper";
  public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";
 
  private AuctionSniperDriver driver; 
  
  public void startBiddingIn(final FakeAuctionServer auction) {
    Thread thread = new Thread("Test Application") { 
	      @Override public void run() {  
	        try { 
	          Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
	        } catch (Exception e) { 
	          e.printStackTrace();  
	        } 
	      } 
	    }; 
	    thread.setDaemon(true); 
	    thread.start(); 

	    makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock();
	    driver = new AuctionSniperDriver(1000);  
	    driver.showsSniperStatus(MainWindow.STATUS_JOINING);
	    driver.hasTitle(MainWindow.APPLICATION_TITLE);   
  }
 
  
  public void showsSniperHasLostAuction() {
	  driver.showsSniperStatus("LOST");
  }
  
  public void hasShownSniperIsBidding() {
	  driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
  }
  
  
  public void stop() {
	  if (driver != null) {
		  driver.dispose();
	  }
  }
  
  public void hasShownSniperIsWinning() {
	  driver.showsSniperStatus(MainWindow.STATUS_WINNING);
  }
  
  public void showsSniperHasWonAuction() {
	  driver.showsSniperStatus(MainWindow.STATUS_WON);
  }
  
  private void makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock() {
	    try {
	      SwingUtilities.invokeAndWait(new Runnable() { public void run() {} });
	    } catch (Exception e) {
	      throw new AssertionError(e);
	    }
	  }
  
}