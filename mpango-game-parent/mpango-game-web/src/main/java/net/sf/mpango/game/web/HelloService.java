package net.sf.mpango.game.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;

@Named
@Singleton
@Service(HelloService.HELLO_SERVICE)
public class HelloService {

    /** Name of the service */
    static final String HELLO_SERVICE = "helloService";
    static final String HELLO_SERVICE_CHANNEL_HELLO = "/service/hello";

    private static final String MSG_PART_NAME = "name";
    protected static final String MSG_PART_GREETING = "greeting";

    @SuppressWarnings("unused")
	@Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init() {
        LOGGER.log(Level.INFO,
                LocalizedMessageBuilder.getSystemMessage(
                        this,
                        MessageConstants.HELLO_SERVICE_INITIALIZATION));
    }

    @Listener(HELLO_SERVICE_CHANNEL_HELLO)
    public void hello(final ServerSession remote, final ServerMessage.Mutable message) {
        LOGGER.log(Level.FINEST,
                LocalizedMessageBuilder.getSystemMessage(
                        this,
                        MessageConstants.HELLO_SERVICE_PROCESS_MSG_RECEIVED));
        //Process the input
        final Map<String, Object> input = message.getDataAsMap();
        final String name = (String)input.get(MSG_PART_NAME);

        //Create the output
        final Map<String, Object> output = new HashMap<String, Object>();
        output.put(MSG_PART_GREETING,
                LocalizedMessageBuilder.getUserMessage(
                        this,
                        Locale.getDefault(),
                        MessageConstants.HELLO_SERVICE_PROCESS_HELLO,
                        name));

        final WebEvent event = new WebEvent("Test event", new Date());
        output.put("event", event);

        remote.deliver(serverSession, HELLO_SERVICE_CHANNEL_HELLO, output, null);
    }

    private static final Logger LOGGER = Logger.getLogger(HelloService.class.getName());
}
