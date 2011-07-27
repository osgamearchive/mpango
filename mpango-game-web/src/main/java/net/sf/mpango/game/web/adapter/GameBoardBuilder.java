package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.game.web.dto.CellDTO;
import net.sf.mpango.game.web.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;

public class GameBoardBuilder extends BaseBuilder<GameBoard, GameBoardDTO> {

	private GameBoardBuilder() {
		super();
	}

	public static GameBoardBuilder instance() {
		return new GameBoardBuilder();
	}
	
	@Override
	public GameBoardDTO build(GameBoard entity) {
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
