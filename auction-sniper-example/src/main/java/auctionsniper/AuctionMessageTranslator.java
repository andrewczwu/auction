package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

	private AuctionEventListener _listener;
	
	public AuctionMessageTranslator(AuctionEventListener listener)
	{
		this._listener = listener;
	}
	@Override
	public void processMessage(Chat chat, Message message) {
		
		if (message.getBody().contains("CLOSE"))
		{
			this._listener.auctionClosed();
		}
		else if (message.getBody().contains("PRICE"))
		{
			this._listener.auctionBid();
		}
	}

}
