package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.common.directory.builder.UserBuilder;
import net.sf.mpango.common.directory.factory.UserFactory;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.web.dto.PlayerDTO;

public class PlayerAdapter extends BaseAdapter<Player, PlayerDTO> {

	private PlayerAdapter() {
		super();
	}

	public static PlayerAdapter instance() {
		return new PlayerAdapter();
	}

	@Override
	public Player fromDTO(PlayerDTO dto) {
		if (dto == null) {
			return null;
		}
		Player player = new Player();
		player.setIdentifier(dto.getId());
		player.setState(dto.getState());
		player.setPosition(PositionFactory.instance().create(dto.getPosition()));
		player.setUnits(UnitFactory.instance().createList(dto.getUnits()));
		player.setUser(UserFactory.instance().create(dto.getUser()));
		return player;
	}

	@Override
	public PlayerDTO toDTO(Player player) {
		if (player == null) {
			return null;
		}
		PlayerDTO dto = new PlayerDTO();
		dto.setId(player.getIdentifier());
		dto.setState(player.getState());
		dto.setPosition(PositionBuilder.instance().build(player.getPosition()));
		dto.setUnits(UnitBuilder.instance().buildList(player.getUnits())); 
		dto.setUser(UserBuilder.instance().build(player.getUser()));
		return dto;
	}

}
