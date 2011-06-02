package net.sourceforge.mpango.dto;

import java.util.List;

public class GameBoardDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -342723072279526556L;
	private Integer rowSize;
	private Integer colSize;
	private List<RowDTO> rows;
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

	public List<RowDTO> getRows() {
		return rows;
	}

	public void setRows(List<RowDTO> rows) {
		this.rows = rows;
	}

	

}
