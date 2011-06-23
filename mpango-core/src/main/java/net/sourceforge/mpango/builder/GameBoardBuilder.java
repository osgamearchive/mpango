package net.sourceforge.mpango.builder;

import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import sun.tools.tree.GreaterOrEqualExpression;

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
            CellDTO cellDTO = CellBuilder.instance().build(cell);
            cellDTOs[cell.getRow()][cell.getColumn()] = cellDTO;
        }
		dto.setCells(cellDTOs);
		return dto;
	}

}
