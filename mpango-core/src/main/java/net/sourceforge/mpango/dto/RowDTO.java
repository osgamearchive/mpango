package net.sourceforge.mpango.dto;

import java.util.List;

public class RowDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4865775368487827970L;

	private List<CellDTO> cells;
	private Integer size;
	private Integer position;

	public List<CellDTO> getCells() {
		return cells;
	}

	public void setCells(List<CellDTO> cells) {
		this.cells = cells;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
