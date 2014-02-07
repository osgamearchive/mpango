package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.entity.Player;

public abstract class PlayerEvent extends AbstractEvent<Player> {

	public PlayerEvent(Player source) {
		super(source);
	}

    public Player getPlayer() {
        return getSource();
    }

    /** Generated serial UID */
    private static final long serialVersionUID = -1492243870146857818L;

}
