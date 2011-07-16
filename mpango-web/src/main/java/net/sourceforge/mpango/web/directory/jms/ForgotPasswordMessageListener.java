package net.sourceforge.mpango.web.directory.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Class that listens to the forgot password queue and proceeds sending the password recovery email to the end user.
 * @author etux
 */
public class ForgotPasswordMessageListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            System.out.println("Received message "+message);
            //TODO Implement the logic of password recovery including sending the email message.
        } else {
            throw new IllegalArgumentException("The message expected must be a TextMessage instance");
        }
    }
}
