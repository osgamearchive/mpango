package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.GameBoard;

public class GameBoardBuilder extends BaseBuilder<GameBoard, GameBoardDTO> {

	@Override
	public GameBoardDTO build(GameBoard entity) {
		GameBoardDTO dto = new GameBoardDTO();
		dto.setId(entity.getIdentifier());
		dto.setColSize(entity.getColSize());
		dto.setRowSize(entity.getRowSize());
		dto.setRows(RowBuilder.instance().buildList(entity.getRows()));
		
		return dto;
	}

}
