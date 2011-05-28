package net.sourceforge.mpango.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.stereotype.Component;

@Component("jmsExceptionListener")
public class JMSExceptionListener implements ExceptionListener {

	@Override
	public void onException(JMSException exception) {
		exception.printStackTrace();
	}

}
