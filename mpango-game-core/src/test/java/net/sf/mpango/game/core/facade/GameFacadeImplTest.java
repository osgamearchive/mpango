package net.sf.mpango.game.core.facade;

import net.sf.mpango.game.core.service.IGameService;

import org.easymock.EasyMock;
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
}
