package net.sf.mpango.game.core.exception;

import net.sf.mpango.game.core.entity.Cell;

public class CellNotCollectableException extends CommandException {
	
	private Cell cell;
	
	public CellNotCollectableException(Cell cell) {
		this.setCell(cell);
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return cell;
	}

	private static final long serialVersionUID = -152430858299121751L;

	

}
