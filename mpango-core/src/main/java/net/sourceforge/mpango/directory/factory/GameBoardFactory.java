package net.sourceforge.mpango.directory.factory;

import java.util.List;

import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.GameBoard;

public class GameBoardFactory extends BaseFactory<GameBoardDTO, GameBoard> {

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
		board.setRows(RowFactory.instance().createList(dto.getRows()));
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
