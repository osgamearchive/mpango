package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import net.sf.mpango.common.entity.AbstractPersistable;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.events.ITurnBasedEntityListener;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.exception.EventNotSupportedException;


/**
 * This class represents the board of the game where all the action happens.
 * The board is thought to be a 2 dimensional board.
 * @author etux
 *
 */
@Entity
@Table(name="GAME_BOARD")
public class GameBoard extends AbstractPersistable implements ITurnBasedEntityListener {
	
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
		GameBoard board = new GameBoard(configuration);
		return generateCells(board);
	}
	
	/**
	 * Factory method that creates a random board based on the row and column sizes.
	 * @param rowSize 
	 * @param colSize
	 * @return
	 */
	public static GameBoard generateRandomBoard(int rowSize, int colSize) {
		BoardConfiguration configuration = new BoardConfiguration(rowSize, colSize);
		return generateRandomBoard(configuration);
	}
	
	/**
	 * Methd that generates the cells for the board (at the moment with dummy random behavior).
	 * @param board
	 * @return
	 */
	private static GameBoard generateCells(GameBoard board) {
		List<Cell> result = null;
		int rowSize = board.getConfiguration().getRowNumber();
		int colSize = board.getConfiguration().getColNumber();
		if  ((rowSize != 0) && (colSize != 0)) {
			result = new ArrayList<Cell>();
			Cell cell = null;
			for (int rowPosition=0; rowPosition < rowSize; rowPosition++) {
				for (int colPosition=0; colPosition < colSize; colPosition++) {
					cell = new Cell(rowPosition, colPosition, null);
					result.add(cell);
				}
			}
		}
		board.setCells(result);
		return board;
	}
	
	/**
	 * Default constructor used when loading the entity from the database.
	 */
	public GameBoard() {
		super();
	}
	
	/**
	 * <p>Constructor that generates a GameBoard based on a GameBoardConfiguration object.</p>
	 * @param configuration
	 */
	private GameBoard(BoardConfiguration configuration) {
		this.rowSize = configuration.getRowNumber();
		this.colSize = configuration.getColNumber();
		this.configuration = configuration;
	}

    public GameBoard(int rowNumber, int colNumber) {
        this.rowSize = rowNumber;
        this.colSize = colNumber;
        this.cells = new ArrayList<Cell>(rowSize * colSize);
        this.arrayOfcells = new Cell[this.rowSize][this.colSize];
        for (int row = 0; row < rowNumber; row++) {
            for (int col = 0; col < colNumber; col++) {
                Cell cell = new Cell(row, col);
                this.cells.add(cell);
                this.arrayOfcells[row][col] = cell;
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

    public Cell getCell(Position position) {
        int rowNumber = convertCoordinateToValidRange(position.getRowNumber(), this.rowSize);
        int colNumber = convertCoordinateToValidRange(position.getColNumber(), this.colSize);
        return getCell(rowNumber, colNumber);
    }

    public static int convertCoordinateToValidRange(int value, int maxSize) {
        if (value < 0) {
            value = (((-value / maxSize) + 1) * maxSize) + value;
        } else if (value >= maxSize) {
            value = (value - ((value / maxSize) * maxSize));
        }
        return value;
    }
	
	public void addCell(Cell cell) {
		this.cells.add(cell);
	}
	
	/**
	 * Turned based entity.
	 * @param turnEvent
	 */
	public void receive(TurnEvent turnEvent) {
		notifyAllListeners(turnEvent);
	}
	
	public void receive(Event event) throws EventNotSupportedException {
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
	/**
	 * Method that sets the cells of the board and initializes the array for fast lookup.
	 * @param cells
	 */
	public void setCells(List<Cell> cells) {
		this.cells = cells;
		this.arrayOfcells = new Cell[configuration.getRowNumber()][configuration.getColNumber()];
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