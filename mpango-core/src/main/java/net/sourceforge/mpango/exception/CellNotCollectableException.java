package net.sourceforge.mpango.exception;

import net.sourceforge.mpango.entity.Cell;

public class CellNotCollectableException extends CommandException {
	
	private Cell cell;
	
	public CellNotCollectableException(Cell cell) {
		this.cell = cell;
	}

	private static final long serialVersionUID = -152430858299121751L;

	

}
