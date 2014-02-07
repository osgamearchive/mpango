package net.sf.mpango.game.web.jms;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.stereotype.Component;

import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.web.GameBoardServiceImpl;

/**
 * Game message listener from the game web part.
 * @author etux
 *
 */
@Component
public class GameBoardServiceListener implements MessageListener {

	@Inject
	private GameBoardServiceImpl gameBoardService;
	
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
			throw new RuntimeException("Message not supported: "+message);
		}
	}

	private void receive(Event event) {
		try {
			gameBoardService.observe(event);
		} catch (EventNotSupportedException e) {
			throw new RuntimeException("Event not supported: "+e);
		}
	}
}