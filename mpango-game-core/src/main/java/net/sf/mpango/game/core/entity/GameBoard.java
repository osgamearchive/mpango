package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.exception.EventNotSupportedException;

import org.apache.commons.lang.math.RandomUtils;
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
    private List<Cell> cells;
	private Cell[][] arrayOfcells;
	private List<Listener> listeners;
	
	public void passTurn(TurnEvent turnEvent) {
		notifyAllListeners(turnEvent);
	}
	
	public void notifyAllListeners(Event event) {
		for (Listener listener : listeners) {
			try {
				listener.receive(event);
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
	
	public GameBoard(BoardConfiguration configuration) {
		this(configuration.getRowNumber(), configuration.getColNumber());
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
        this.cells = new ArrayList<Cell>(rowSize*colSize);
		this.arrayOfcells = new Cell[this.rowSize][this.colSize];
		for (int row=0; row<rowNumber; row++) {
			for (int col=0; col<colNumber; col++) {
                Cell cell = new Cell(row, col);
                cell.setAttackBonus(RandomUtils.nextFloat());
                cell.setDefenseBonus(RandomUtils.nextFloat());
                cell.setResources(new HashSet<Resources>());
                cell.setConstructions(new ArrayList<Construction>());
                this.cells.add(cell);
				this.arrayOfcells[row][col] = cell;
			}
		}
	}

	/**
	 * default constructor
	 */
	public GameBoard() {
		super();
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
		return arrayOfcells[rowNumber][colNumber];
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
	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
        for (Cell cell : cells) {
            this.arrayOfcells[cell.getRow()][cell.getColumn()] = cell;
        }
	}
	
}
