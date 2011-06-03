package net.sourceforge.mpango.directory.factory;

import java.util.List;

import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;

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
		GameBoard board = new GameBoard();
		board.setColSize(dto.getColSize());
		board.setRowSize(dto.getRowSize());
		board.setIdentifier(dto.getId());
		Cell[][] cells = new Cell[board.getRowSize()][board.getColSize()];
		CellDTO[][] dtos = dto.getCells();
		for (int i = 0; i < dtos.length; i++) { // Iterate over the rows
			for (int j = 0; j < dtos[i].length; j++) { // Iterate over the cols
				Cell cell = CellFactory.instance().create(dtos[i][j]);
				cells[i][j] = cell;
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
