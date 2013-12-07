package net.sf.mpango.game.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import net.sf.mpango.game.core.events.Event;

import org.springframework.jms.core.MessageCreator;

public class EventBasedMessageCreator implements MessageCreator {

	private Event event;
	
	public EventBasedMessageCreator(Event event) {
		this.event = event;
	}
	
	@Override
	public Message createMessage(Session session) throws JMSException {
		Message message = session.createObjectMessage(event);
		return message;
	}

}
