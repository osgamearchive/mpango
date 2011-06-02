package net.sourceforge.mpango.entity;

public class BoardConfiguration {

	private int rowNumber;
	private int colNumber;
	
	public BoardConfiguration(int rowNumber, int colNumber) {
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}
	
	public int getColNumber() {
		return colNumber;
	}

}
