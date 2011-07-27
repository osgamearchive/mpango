package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;

/**
 * 
 * @author etux
 *
 */
public class GameBoardAdapter extends BaseAdapter<GameBoard, GameBoardDTO> {

	private GameBoardAdapter() {
		super();
	}

	public static GameBoardAdapter instance() {
		return new GameBoardAdapter();
	}

	@Override
	public GameBoard fromDTO(GameBoardDTO dto) {
		throw new RuntimeException("This operation is not supported and this exception should only occur at development time");
	}

	@Override
	public GameBoardDTO toDTO(GameBoard entity) {
		if (entity == null) {
			return null;
		}
		GameBoardDTO dto = new GameBoardDTO();
		dto.setId(entity.getIdentifier());
		dto.setColSize(entity.getColSize());
		dto.setRowSize(entity.getRowSize());
		CellDTO[][] cellDTOs = new CellDTO[entity.getRowSize()][entity.getColSize()];
        for (Cell cell : entity.getCells()) {
            CellDTO cellDTO = CellAdapter.instance().toDTO(cell);
            cellDTOs[cell.getRow()][cell.getColumn()] = cellDTO;
        }
		dto.setCells(cellDTOs);
		return dto;
	}

}
