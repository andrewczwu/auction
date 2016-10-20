package test.endtoend.auctionsniper;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuctionSniperEndToEndTest {
	  private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	 // private final FakeAuctionServer auction2 = new FakeAuctionServer("item-65432");  

	  private final ApplicationRunner application = new ApplicationRunner(); 
	  
	  @Test public void 
	  sniperJoinsAuctionUntilAuctionCloses() throws Exception { 
	    auction.startSellingItem();                
	    application.startBiddingIn(auction);       
	    auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
	    auction.announceClosed();                  
	    application.showsSniperHasLostAuction();   
	  } 
	  
	  
	  @Test public void
	  sniperMakesAHigherBidButLoses() throws Exception {
		  auction.startSellingItem();
		  application.startBiddingIn(auction);
		  auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		  auction.reportPrice(1000, 98, "other bidder");
		  application.hasShownSniperIsBidding();
		  auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		  auction.announceClosed();
		  application.showsSniperHasLostAuction();
	  }
	  

}
