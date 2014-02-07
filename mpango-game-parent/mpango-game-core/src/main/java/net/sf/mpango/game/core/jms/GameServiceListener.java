package net.sf.mpango.game.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.mpango.game.core.commands.Command;
import net.sf.mpango.game.core.commands.PlayerCommand;
import net.sf.mpango.game.core.commands.UnitCommand;
import net.sf.mpango.game.core.service.IGameService;

@Component
public class GameServiceListener implements MessageListener {

	@Autowired
	private IGameService gameService;
    
    private void executeCommand(Command command) {
    	if (command instanceof PlayerCommand) {
    		PlayerCommand playerCommand = (PlayerCommand) command;
    		executeCommand(playerCommand);
    	} else if (command instanceof UnitCommand) {
    		UnitCommand unitCommand = (UnitCommand) command;
    		executeCommand(unitCommand);
    	}
    }
    
    private void executeCommand(PlayerCommand command) {
    	System.err.println("To be implemented");
    }
    
    private void executeCommand(UnitCommand command) {
    	System.err.println("To be implemented");
    }

	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				Command command = (Command) objectMessage.getObject();
				executeCommand(command);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Message not supported");
		}
	}

	public IGameService getGameService() {
		return gameService;
	}

	public void setGameService(IGameService gameService) {
		this.gameService = gameService;
	}

}
