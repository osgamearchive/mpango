package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.entity.Player;

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

		return player;
	}

}
