/**
 * 
 */
package net.sf.mpango.game.web.dto;

import net.sf.mpango.common.dto.BaseDTO;

/**
 * @author aplause
 * 
 */
public class PositionDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7273481200262837563L;

	private int rowNumber;
	private int colNumber;

	/**
	 * @return
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return
	 */
	public int getColNumber() {
		return colNumber;
	}

	/**
	 * @param colNumber
	 */
	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

}
