package net.sourceforge.mpango.builder;

import net.sourceforge.mpango.actions.Command;
import net.sourceforge.mpango.dto.CommandDTO;

public class CommandBuilder extends BaseBuilder<Command, CommandDTO>{
	
	private CommandBuilder() {
		super();
	}

	public static CommandBuilder instance() {
		return new CommandBuilder();
	}
	@Override
	public CommandDTO build(Command command) {
		// TODO Auto-generated method stub		
		
		return new CommandDTO();
	}

}
