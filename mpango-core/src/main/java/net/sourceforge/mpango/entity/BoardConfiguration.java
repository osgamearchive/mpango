package net.sourceforge.mpango.entity;

/**
 * <p>The board configuration has all the configuration parameters for the board of the game.</p>
 * @author edvera
 */
public class BoardConfiguration {

    private static final int MAX_ROW_NUMBER = 10;
    private static final int MAX_COL_NUMBER = 10;
	private int rowNumber;
	private int colNumber;

    public BoardConfiguration() {
        this(MAX_ROW_NUMBER, MAX_COL_NUMBER);
    }

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
