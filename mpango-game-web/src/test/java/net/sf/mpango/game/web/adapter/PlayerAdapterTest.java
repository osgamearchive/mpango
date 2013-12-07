package net.sf.mpango.game.web.adapter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Position;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.web.adapter.PlayerAdapter;
import net.sf.mpango.game.web.dto.PlayerDTO;
import net.sf.mpango.game.web.TestUtils;

import org.junit.Test;

public class PlayerAdapterTest {

	@Test
	public void testBuildPlayerNullObj() {
		Player player = TestUtils.getPlayer();
		PlayerDTO dto = PlayerAdapter.instance().toDTO(player);
		assertNotNull(dto);
		assertEquals(dto.getState(), player.getState());
		assertNull(dto.getUnits());
		assertNull(dto.getPosition());
		assertNull(dto.getUser());
	}
	
	//@Test
	public void testBuildPlayerObj() {
		Player player = TestUtils.getPlayer();
		player.setPosition(new Position(1, 2));
		List<Unit> unitList = new ArrayList<Unit>();
		unitList.add(TestUtils.getUnit());
		unitList.add(TestUtils.getUnit());
		unitList.add(TestUtils.getUnit());
		player.setUnits(unitList);
		player.setUser(new User());
		PlayerDTO dto = PlayerAdapter.instance().toDTO(player);
		
		assertNotNull("dto.getPosition() is null", dto.getPosition());
		assertEquals(dto.getPosition().getRowNumber(), 1);
		assertEquals(dto.getPosition().getColNumber(), 2);
		assertNotNull("dto.Units() is null", dto.getUnits());
		assertEquals(dto.getUnits().size(), 3);
		assertNotNull(dto.getUser());		
		
	}

	@Test
	public void testBuildList() {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player());
		playerList.add(new Player());
		playerList.add(new Player());
		playerList.add(new Player());
		playerList.add(new Player());
		List<PlayerDTO> dtoList = PlayerAdapter.instance().toDTOList(playerList);
		assertNotNull(dtoList);
		assertEquals(dtoList.size(), 5);
	}

}
