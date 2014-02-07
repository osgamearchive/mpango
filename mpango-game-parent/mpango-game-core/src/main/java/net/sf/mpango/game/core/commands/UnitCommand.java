package net.sf.mpango.game.core.commands;

import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.events.CommandEvent;

public interface UnitCommand<T extends CommandEvent> extends Command<T> {

	Unit getUnit();
}
