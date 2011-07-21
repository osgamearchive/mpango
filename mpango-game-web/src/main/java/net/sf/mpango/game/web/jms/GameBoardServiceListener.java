package net.sf.mpango.game.web.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.stereotype.Component;

import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.web.GameBoardService;

/**
 * Game message listener from the game web part.
 * @author etux
 *
 */
@Component
public class GameBoardServiceListener implements MessageListener {

	@Inject
	private GameBoardService gameBoardService;
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				Event event = (Event) objectMessage.getObject();
				receive(event);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			
		}
	}

	private void receive(Event event) {
		try {
			gameBoardService.receiveEvent(event);
		} catch (EventNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}