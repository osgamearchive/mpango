package net.sf.mpango.game.core.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.dto.CellDTO;
import net.sf.mpango.game.core.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;

public class GameBoardFactory extends BaseFactory<GameBoardDTO, GameBoard> {

	private GameBoardFactory() {
		super();
	}

	public static GameBoardFactory instance() {
		return new GameBoardFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge
	 * .mpango.dto.BaseDTO)
	 */
	@Override
	public GameBoard create(GameBoardDTO dto) {
		GameBoard board = new GameBoard(dto.getRowSize(), dto.getColSize());
		board.setIdentifier(dto.getId());
		ArrayList<Cell> cells = new ArrayList<Cell> (board.getRowSize()*board.getColSize());
		CellDTO[][] dtos = dto.getCells();
		for (int i = 0; i < dtos.length; i++) { // Iterate over the rows
			for (int j = 0; j < dtos[i].length; j++) { // Iterate over the cols
				Cell cell = CellFactory.instance().create(dtos[i][j]);
				cells.add(cell);
			}
		}
		board.setCells(cells);
		return board;
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
