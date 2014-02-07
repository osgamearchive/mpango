package net.sf.mpango.game.core.commands;

import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.events.CommandEvent;

public interface PlayerCommand<T extends CommandEvent> extends Command<T> {

	Player getPlayer();
}
