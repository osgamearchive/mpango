package net.sourceforge.mpango.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.events.TurnEvent;
import net.sourceforge.mpango.exception.EventNotSupportedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * This class represents the board of the game where all the action happens.
 * The board is thought to be a 2 dimensional board.
 * @author etux
 *
 */
@Entity
@Table(name="GAME_BOARD")
public class GameBoard extends AbstractPersistable {
	
	private static final Log logger = LogFactory.getLog(GameBoard.class);
	private int rowSize;
	private int colSize;
	private List<Row> rows;
	private List<Listener> listeners;
	
	public void passTurn(TurnEvent turnEvent) {
		notifyAllListeners(turnEvent);
	}
	
	private void notifyAllListeners(Event event) {
		for (Listener listener : listeners) {
			try {
				listener.receiveEvent(event);
			} catch (EventNotSupportedException e) {
				logger.error("Error on listener [" + listener + "]", e);
			}
		}
	}
	
	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Constructor to create a GameBoard
	 * @param rowNumber number of rows for the game board
	 * @param colNumber number of columns for the game board
	 */
	public GameBoard (int rowNumber, int colNumber) {
		logger.debug("Creating game board with " + rowNumber + " rows and " + colNumber + " columns");
		this.rowSize = rowNumber;
		this.colSize = colNumber;
		this.rows = new ArrayList<Row>(rowNumber);
		for (int i=0; i<rowNumber; i++) {
			this.rows.add(new Row(i, this.colSize));
		}
	}
	/**
	 * Obtains the cell by row and column.
	 * @param rowNumber
	 * @param colNumber
	 * @throws java.lang.IllegalArgumentException in case the row or the column are not in a valid range.
	 * @return
	 */
	public Cell getCell (int rowNumber, int colNumber) {
		if ((this.rowSize <= rowNumber) || (this.colSize <= colNumber)) {
			throw new IllegalArgumentException("The specified cell is not found in the game board");
		}
		return rows.get(rowNumber).getCell(colNumber);
	}
	@Column
	public int getRowSize() {
		return rowSize;
	}
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
	@Column
	public int getColSize() {
		return colSize;
	}
	public void setColSize(int colSize) {
		this.colSize = colSize;
	}
	@OneToMany
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	
}
