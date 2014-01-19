package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.mpango.common.entity.AbstractEntity;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.ITurnBasedEntityListener;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.events.TurnEvent;
import net.sf.mpango.game.core.exception.EventNotSupportedException;


/**
 * This class represents the board of the game where all the action happens.
 * The board is thought to be a 2 dimensional board.
 * @author etux
 *
 */
@Entity
public class GameBoard extends AbstractEntity<Long> implements ITurnBasedEntityListener {

    private static final int DEFAULT_COL_NUMBER = 10;
    private static final int DEFAULT_ROW_NUMBER = 10;

    private BoardConfiguration configuration;
    private List<Cell> cells;
	private List<Listener> listeners;
	
	/**
	 * Constructor that generates an instance randomly.
	 * @param configuration
	 * @return
	 */
	public static GameBoard generateRandomBoard (final BoardConfiguration configuration) {
		GameBoard board = new GameBoard(configuration);
        return board;
	}
	
	/**
	 * Methd that generates the cells for the board (at the moment with dummy random behavior).
	 * @return
	 */
	private void generateCells() {
		if  ((configuration != null) && (configuration.getRowNumber() != 0) && (configuration.getColNumber() != 0)) {
			Cell cell = null;
			for (int rowPosition=0; rowPosition < configuration.getRowNumber(); rowPosition++) {
				for (int colPosition=0; colPosition < configuration.getColNumber(); colPosition++) {
					cell = new Cell(rowPosition, colPosition);
					cells.add(cell);
				}
			}
		}
	}
	
	/**
	 * Default constructor used when loading the entity from the database.
	 */
	public GameBoard() {
        this(new BoardConfiguration(DEFAULT_ROW_NUMBER, DEFAULT_COL_NUMBER));
    }

	/**
	 * <p>Constructor that generates a GameBoard based on a GameBoardConfiguration object.</p>
	 * @param configuration
	 */
	private GameBoard(final BoardConfiguration configuration) {
        assert configuration != null;

        this.configuration = configuration;
        cells = new ArrayList<>(configuration.getColNumber() * configuration.getRowNumber());
        listeners = new LinkedList<>();
        generateCells();
	}

	/**
	 * Obtains the cell by row and column.
	 * @param rowNumber
	 * @param colNumber
	 * @throws java.lang.IllegalArgumentException in case the row or the column are not in a valid range.
	 * @return
	 */
	private Cell getCell (final int rowNumber, final int colNumber) {
		if ((configuration.getRowNumber() <= rowNumber) || (configuration.getColNumber() <= colNumber)) {
			throw new IllegalArgumentException("The specified cell is not found in the game board");
		}
        return cells.get(rowNumber * configuration.getRowNumber() + colNumber);
	}

    @Transient
    public Cell getCell(final Position position) {
        return getCell(position.getRowNumber(), position.getColNumber());
    }
	
	/**
	 * Turned based entity.
	 * @param turnEvent
	 */
	public void receive(final TurnEvent turnEvent) {
		notifyAllListeners(turnEvent);
	}
	
	public void receive(final Event event) throws EventNotSupportedException {
		throw new EventNotSupportedException(event);
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
	}

	public BoardConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(final BoardConfiguration configuration) {
		this.configuration = configuration;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Listener related methods															//
	//////////////////////////////////////////////////////////////////////////////////////
	public void notifyAllListeners(final Event event) {
		for (Listener listener : listeners) {
			try {
				listener.receive(event);
			} catch (EventNotSupportedException e) {
			}
		}
	}
	
	public void addListener(final Listener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(final Listener listener) {
		this.listeners.remove(listener);
	}
	
}