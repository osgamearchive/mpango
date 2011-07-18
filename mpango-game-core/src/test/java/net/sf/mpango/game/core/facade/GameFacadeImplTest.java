package net.sf.mpango.game.core.facade;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.factory.UserFactory;
import net.sf.mpango.game.core.TestUtils;
import net.sf.mpango.game.core.dto.PlayerDTO;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.exception.PlayerAlreadyExistsException;
import net.sf.mpango.game.core.factory.PlayerFactory;
import net.sf.mpango.game.core.service.IGameService;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class GameFacadeImplTest {

	private GameFacadeImpl gameFacade;
	private IGameService service;
	
	@Before
	public void setUp() {
		gameFacade = new GameFacadeImpl();
		service = EasyMock.createMock(IGameService.class);
		gameFacade.setGameService(service);
	}
	
	@Test
	public void createPlayerTest() throws PlayerAlreadyExistsException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("email@domain.com");
		PlayerDTO playerDTO = TestUtils.getPlayerDTO();
		playerDTO.setName("name");
		playerDTO.setUser(userDTO);
		User user = UserFactory.instance().create(userDTO);
		Player player = PlayerFactory.instance().create(playerDTO);
		EasyMock.expect(service.createPlayer(EasyMock.eq(user), EasyMock.eq(player))).andReturn(player);
		EasyMock.replay(service);
		PlayerDTO resultingPlayerDTO = gameFacade.createPlayer(userDTO, playerDTO);
		Assert.assertEquals("The resulting player should be the same as the provided one", resultingPlayerDTO, playerDTO);
		EasyMock.verify(service);
	}
}
