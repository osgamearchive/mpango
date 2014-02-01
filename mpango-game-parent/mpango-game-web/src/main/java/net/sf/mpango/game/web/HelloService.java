package net.sf.mpango.game.web;

import java.util.Date;
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

@Named
@Singleton
@Service("helloService")
public class HelloService
{
    @SuppressWarnings("unused")
	@Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init()
    {
        System.out.print("initializing hello service");
    }

    @Listener("/service/hello")
    public void processHello(ServerSession remote, ServerMessage.Mutable message)
    {
        System.out.println("Message received");
        Map<String, Object> input = message.getDataAsMap();
        String name = (String)input.get("name");

        Map<String, Object> output = new HashMap<String, Object>();
        output.put("greeting", "Hello, " + name);
        WebEvent event = new WebEvent("Test event", new Date());
        output.put("event", event);
        remote.deliver(serverSession, "/hello", output, null);
    }
}
