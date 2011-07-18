package net.sf.mpango.game.core.builder;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.common.directory.builder.UserBuilder;
import net.sf.mpango.game.core.dto.PlayerDTO;
import net.sf.mpango.game.core.entity.Player;

public class PlayerBuilder extends BaseBuilder<Player, PlayerDTO> {

	private PlayerBuilder() {
		super();
	}

	public static PlayerBuilder instance() {
		return new PlayerBuilder();
	}

	@Override
	public PlayerDTO build(Player player) {
		PlayerDTO dto = new PlayerDTO();
		dto.setId(player.getIdentifier());
		dto.setName(player.getName());
		dto.setState(player.getState());

		if (null != player.getPosition()) {
			dto.setPosition(PositionBuilder.instance().build(
					player.getPosition()));
		}

		if (null != player.getUnits()) {
			dto.setUnits(UnitBuilder.instance().buildList(player.getUnits()));
		}
		
		if (null != player.getUser()) {
			dto.setUser(UserBuilder.instance().build(player.getUser()));
		}
		
		return dto;
	}

}
