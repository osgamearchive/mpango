package net.sf.mpango.game.core.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Position {

	private int rowNumber;
	private int colNumber;

    public Position() {}
    
	public Position(int rowNumber, int colNumber) {
		this.setRowNumber(rowNumber);
		this.setColNumber(colNumber);
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	public int getColNumber() {
		return colNumber;
	}
}
