package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.entity.Player;

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
		dto.setState(dto.getState());

		if (null != player.getPosition()) {
			dto.setPosition(PositionBuilder.instance().build(
					player.getPosition()));
		}

		if (null != player.getUnits()) {
			dto.setUnits(UnitBuilder.instance().buildList(player.getUnits()));
		}
		return dto;
	}

}