package test.endtoend.auctionsniper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.Main;

//import auctionsniper.xmpp.XMPPAuction;

public class FakeAuctionServer {
	  public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	  public static final String AUCTION_RESOURCE = "Auction";
	  public static final String XMPP_HOSTNAME = "localhost";
	  private static final String AUCTION_PASSWORD = "auction";
	  
	  private final String itemId;
	  private final XMPPConnection connection;
	  private Chat currentChat;
	  private final SingleMessageListener messageListener = new SingleMessageListener();
	  
	  public FakeAuctionServer(String itemId) {
	    this.itemId = itemId;
	    this.connection = new XMPPConnection(XMPP_HOSTNAME);
	  }	 	 
	  
	  public void startSellingItem() throws XMPPException {
		    connection.connect();
		    connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD,
		        AUCTION_RESOURCE);
		    connection.getChatManager().addChatListener(new ChatManagerListener() {
		      public void chatCreated(Chat chat, boolean createdLocally) {
		        currentChat = chat;	
		        chat.addMessageListener(messageListener);
		      }
		    });
		   
		  }
	  
	  public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		  currentChat.sendMessage(String.format("SOLVERSION: 1.1; Event: PRICE; " +
				  "CurrentPrice: %d; Increment: %d; Bidder: %s",
				  price, increment, bidder));
	  }
	  
	  public void hasReceivedJoinRequestFrom(String sniperId)
			  throws InterruptedException {
		  receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	  }
	  
	  public void hasReceivedBid(int bid, String sniperId) 
			  throws InterruptedException {
		  receivesAMessageMatching(sniperId, equalTo(String.format(Main.BID_COMMAND_FORMAT, bid)));			  
	  }
	  
	  public void announceClosed() throws XMPPException {
		  currentChat.sendMessage(Main.CLOSE_COMMAND_FORMAT);
	  }
	  
	  public String getItemId() {
		  return itemId;
	  }
	  
	   public void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) 
			   throws InterruptedException { 
		  messageListener.receivesAMessage(messageMatcher);
		  assertThat(currentChat.getParticipant(), equalTo(sniperId));
	   } 
	   
	  public class SingleMessageListener implements MessageListener {
		   private final ArrayBlockingQueue<Message> messages = 
                    new ArrayBlockingQueue<Message>(1); 
		   public void processMessage(Chat chat, Message message) { 
			   messages.add(message); 
		   } 
		   public void receivesAMessage(Matcher<? super String> messageMatcher)
		    throws InterruptedException
		    {
			   final Message message = messages.poll(5, TimeUnit.SECONDS);
			   assertThat("Message", message, is(notNullValue()));
			   assertThat(message.getBody(), messageMatcher);
		    
		    }

	  }
}
