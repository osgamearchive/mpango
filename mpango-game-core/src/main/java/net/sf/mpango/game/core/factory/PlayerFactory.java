package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.directory.factory.UserFactory;
import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.dto.PlayerDTO;
import net.sf.mpango.game.core.entity.Player;

/**
 * 
 * @author aplause
 * 
 */
public class PlayerFactory extends BaseFactory<PlayerDTO, Player> {

	private PlayerFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return
	 */
	public static PlayerFactory instance() {
		return new PlayerFactory();

	}

	@Override
	public Player create(PlayerDTO dto) {
		Player player = new Player();
		player.setIdentifier(dto.getId());
		player.setName(dto.getName());
		player.setState(dto.getState());
		
		if (null != dto.getPosition()) {
			player.setPosition(PositionFactory.instance().create(
					dto.getPosition()));
		}
		
		if (null != dto.getUnits()) {
			player.setUnits(UnitFactory.instance().createList(dto.getUnits()));
		}

		player.setUser(UserFactory.instance().create(dto.getUser()));
		
		return player;
	}

}
