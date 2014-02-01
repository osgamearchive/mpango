package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.entity.Unit;

public interface UnitCommand extends Command {

	Unit getUnit();
}
