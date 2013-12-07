package net.sf.mpango.game.web.dto;

import net.sf.mpango.common.dto.BaseDTO;


public class GameBoardDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -342723072279526556L;
	private Integer rowSize;
	private Integer colSize;
	private CellDTO[][] cells;
	
	//private List<ListenerDTO> listeners;

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getColSize() {
		return colSize;
	}

	public void setColSize(Integer colSize) {
		this.colSize = colSize;
	}

	public CellDTO[][] getCells() {
		return cells;
	}
	
	public void setCells(CellDTO[][] cells) {
		this.cells = cells;
	}
}