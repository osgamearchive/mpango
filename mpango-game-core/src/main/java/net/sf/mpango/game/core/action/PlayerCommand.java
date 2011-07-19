package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.entity.Player;

public interface PlayerCommand extends Command {

	Player getPlayer();
}
