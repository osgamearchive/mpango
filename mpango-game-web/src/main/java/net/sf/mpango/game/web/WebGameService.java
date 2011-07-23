package net.sf.mpango.game.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;

import org.apache.log4j.Logger;

@Named
@Singleton
@Service("WebGameService")
public class WebGameService {

    private static final String PARAM_NAME_USERNAME = "username";
    private static final String PARAM_NAME_EVENT = "event";
    private static final String EVENT_JOIN = "JOIN";
    private static final String EVENT_DEPART = "DEPART";

    @SuppressWarnings("unused")
	@Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init() {
        logger.debug("Initializing service: " + this.getClass().getAnnotation(Service.class).toString());
    }

    @Listener("/service/game")
    public void processGame (ServerSession remote, ServerMessage.Mutable message) {

        Map<String, Object> input = message.getDataAsMap();
        String event = (String) input.get(PARAM_NAME_EVENT);
        String user = (String) input.get(PARAM_NAME_USERNAME);

        Map<String, Object> output = new HashMap<String, Object>();
        if (EVENT_JOIN.equals(event)) {
            output.put("greeting", "User "+user+" has joined the game.");
        } else if (EVENT_DEPART.equals(event)) {
            output.put("greeting", "User "+user+" departed the game.");
        } else {
            output.put("greeting", "Unknown event.");
        }
        remote.deliver(serverSession, "/game", output, null);
    }

    private static final Logger logger = Logger.getLogger(WebGameService.class);
}
