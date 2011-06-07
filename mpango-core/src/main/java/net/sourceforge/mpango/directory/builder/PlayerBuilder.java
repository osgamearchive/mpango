package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.entity.Player;

public class PlayerBuilder extends BaseBuilder<Player, PlayerDTO> {

	private PlayerBuilder() {
		super();
	}

	@Override
	public PlayerDTO build(Player player) {
		PlayerDTO dto = new PlayerDTO();
		dto.setId(player.getIdentifier());
		dto.setName(player.getName());
		dto
				.setPosition(PositionBuilder.instance().build(
						player.getPosition()));
		dto.setState(dto.getState());
		dto.setUnits(UnitBuilder.instance().buildList(player.getUnits()));

		return dto;
	}

}
