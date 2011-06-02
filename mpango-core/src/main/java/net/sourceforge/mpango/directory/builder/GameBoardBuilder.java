package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.GameBoard;

public class GameBoardBuilder extends BaseBuilder<GameBoard, GameBoardDTO> {

	@Override
	public GameBoardDTO build(GameBoard entity) {
		GameBoardDTO dto = new GameBoardDTO();
		dto.setId(entity.getIdentifier());
		dto.setColSize(entity.getColSize());
		dto.setRowSize(entity.getRowSize());
		CellDTO[][] cellDTOs = new CellDTO[entity.getRowSize()][entity.getColSize()];
		for (int i=0;i<entity.getCells().length; i++) {
			for (int j=0;j<entity.getCells()[i].length; j++) {
				CellDTO cell = CellBuilder.instance().build(entity.getCells()[i][j]);
				cellDTOs[i][j] = cell;
			}
		}
		dto.setCells(cellDTOs);
		return dto;
	}

}
