package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

import net.sf.mpango.common.entity.AbstractPersistable;
import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.events.ITurnBasedEntityListener;
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
public class GameBoard extends AbstractPersistable implements ITurnBasedEntityListener {
	
	private static final Log logger = LogFactory.getLog(GameBoard.class);
	private BoardConfiguration configuration;
	private int rowSize;
	private int colSize;
    private List<Cell> cells;
	private Cell[][] arrayOfcells;
	private List<Listener> listeners;
	
	/**
	 * Constructor that generates an instance randomly.
	 * @param configuration
	 * @return
	 */
	public static GameBoard generateRandomBoard (BoardConfiguration configuration) {
		System.out.println("Generating a board of size: "+configuration.getRowNumber()+" x "+configuration.getColNumber());
		GameBoard board = new GameBoard(configuration);
		List<Cell> cells = new ArrayList<Cell>();
		Cell cell = null;
		for (int i=0; i<configuration.getRowNumber(); i++) {
			for (int j=0; i<configuration.getColNumber(); j++) {
				cell = new Cell(i, j, null);
				cells.add(cell);
			}
		}
		board.setCells(cells);
		return board;
	}
	
	/**
	 * Default constructor used when loading the entity from the database.
	 */
	public GameBoard() {
		super();
	}
	
	/**
	 * Constructor in order to create the board for the first time.
	 * @param configuration
	 */
	public GameBoard(BoardConfiguration configuration) {
		this(configuration.getRowNumber(), configuration.getColNumber());
		this.configuration = configuration;
	}
	
	/**
	 * <p>Constructor that generates a GameBoard.</p>
	 * TODO incorporate Serg's code here.
	 * @param rowNumber number of rows for the game board
	 * @param colNumber number of columns for the game board
	 */
	private GameBoard (int rowNumber, int colNumber) {
		logger.debug("Creating game board with " + rowNumber + " rows and " + colNumber + " columns");
		this.rowSize = rowNumber;
		this.colSize = colNumber;
        this.cells = new ArrayList<Cell>(rowSize*colSize);
		this.arrayOfcells = new Cell[this.rowSize][this.colSize];
		for (int row = 0; row < rowNumber; row++) {
			for (int col = 0; col < colNumber; col++) {
                Cell cell = new Cell(row, col);
                cell.setAttackBonus(RandomUtils.nextFloat());
                cell.setDefenseBonus(RandomUtils.nextFloat());
                cell.setResources(new HashSet<Resources>());
                cell.setConstructions(new ArrayList<Construction>());
				this.arrayOfcells[row][col] = cell;
                this.addCell(cell);
			}
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
		return arrayOfcells[rowNumber][colNumber];
	}
	
	public void addCell(Cell cell) {
		this.cells.add(cell);
	}
	
	/**
	 * Turned based entity.
	 * @param turnEvent
	 */
	public void receiveEvent(TurnEvent turnEvent) {
		notifyAllListeners(turnEvent);
	}
	
	public void receiveEvent(Event event) throws EventNotSupportedException {
		throw new EventNotSupportedException(event);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Entity relationships																//
	//////////////////////////////////////////////////////////////////////////////////////
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
	@OneToOne
	public BoardConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(BoardConfiguration configuration) {
		this.configuration = configuration;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Listener related methods															//
	//////////////////////////////////////////////////////////////////////////////////////
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
	
}
