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

    /**
     * Obtains the cell by row and column.
     *
     * @param rowNumber
     * @param colNumber
     * @return in case the row or the column are out of range, converts it and avoid exception.
     *         This method is needed to rotate the view around the board and find the shortest path.
     */
    public Cell getCell(int rowNumber, int colNumber) {
        rowNumber = convertCoordToValidRange(rowNumber, this.rowSize);
        colNumber = convertCoordToValidRange(colNumber, this.colSize);
        return arrayOfcells[rowNumber][colNumber];
    }

    public int isLarger(int a, int b) {
        if (a > b) {
            return 1;
        } else return -1;
    }

    public ArrayList<Cell> getWay(Cell start, Cell goal) {
        return getWay(start, goal, true);
    }

    public ArrayList<Cell> getWay(Cell start, Cell goal, boolean shortest) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int goalRow;
        int goalCol;
        if (shortest) {
            goalRow = obtainCorrectDirection(startRow, goal.getRow(), this.rowSize);
            goalCol = obtainCorrectDirection(startCol, goal.getColumn(), this.colSize);
        } else {
            goalRow = goal.getRow();
            goalCol = goal.getColumn();
        }
        int colSign = isLarger(goalCol, startCol);
        int rowSign = isLarger(goalRow, startRow);

        int rows = goalRow - startRow;
        int cols = goalCol - startCol;
        double direction = (double) rows / cols;
        int pointsNumber = Math.abs(rows) + Math.abs(cols);

        for (int i = 0; i < pointsNumber; i++) {
            if (goalCol == startCol) {
                startRow = startRow + rowSign;
                cells.add(getCell(startCol, startRow));
            } else if (goalRow == startRow) {
                startCol = startCol + colSign;
                cells.add(getCell(startCol, startRow));
            } else {
                double d1 = direction - (double) (goalRow - startRow + rowSign) / (goalCol - startCol);
                double d2 = direction - (double) (goalRow - startRow) / (goalCol - startCol + colSign);
                if (Math.abs(d1) > Math.abs(d2)) {
                    startRow = startRow + rowSign;
                } else {
                    startCol = startCol + colSign;
                }
                cells.add(getCell(startCol, startRow));
            }
        }
        return cells;
    }

    public int obtainCorrectDirection(int start, int goal, int maxSize) {
        if (maxSize - goal + start < goal - start) {
            goal = -maxSize + goal;
        } else if (start - goal > maxSize - (start - goal)) {
            goal = maxSize + goal;
        }
        return goal;
    }

    public int convertCoordToValidRange(int coord, int maxSize) {
        if (coord < 0) {
            coord = (((-coord / maxSize) + 1) * maxSize) + coord;
        } else if (coord >= maxSize) {
            coord = (coord - ((coord / maxSize) * maxSize));
        }
        return coord;
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
