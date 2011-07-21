package net.sf.mpango.game.core.factory;

import java.util.List;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.GameBoard;

public class GameBoardFactory extends BaseFactory<GameBoardDTO, GameBoard> {

	private GameBoardFactory() {
		super();
	}

	public static GameBoardFactory instance() {
		return new GameBoardFactory();
	}

	@Override
	public GameBoard create(GameBoardDTO dto) {
		throw new RuntimeException("This operation is not supported and this exception should only occur at development time");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#createList(java.
	 * util.List)
	 */
	@Override
	public List<GameBoard> createList(List<GameBoardDTO> dtoList) {
		/*
		 * cannot create list of boards
		 */
		return null;
	}

}
