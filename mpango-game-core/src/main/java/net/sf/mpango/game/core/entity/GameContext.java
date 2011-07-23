package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import net.sf.mpango.common.entity.AbstractPersistable;

import org.apache.commons.lang.math.RandomUtils;

/**
 * <p>Object that controls the state of the game and offers functionality around it.
 * By the state of the game, we mean:
 * <ol>
 * 	<li>- List of players: that are associated with this context.</li>
 *  <li>- Game baard: with which the users interact.</li>
 *  <li>- Game configuration: configuration for the game.</li>
 * </ol>
 * </p>
 * @author etux
 *
 */
@Entity
public class GameContext extends AbstractPersistable {

	protected List<Player> players = new ArrayList<Player>();
	protected GameBoard board;
	protected GameConfiguration configuration;

	public GameContext() {
		configuration = new GameConfiguration(new BoardConfiguration(10, 10));
	}
	
	public void initialize() {}

	/**
	 * Method called when a player joins the game.
	 * @param player
	 */
	public void join(Player player) {
		players.add(player);
	}

	/**
	 * Method used to generate the starting units for a recently joined player.
	 * @return List of units for the player to start with.
	 */
	public List<Unit> generateStartingUnits() {
		List<Unit> units = new ArrayList<Unit>();
        units.add(new Unit());
		return units;
	}

	/**
	 * Method used to generate a random position on the map.
	 * TODO Ideally this random position should be calculated so that the player gets enough space away from his neighbors.
	 * @return random position.
	 */
	public Position generateRandomPosition() {
		Position position = new Position(
				RandomUtils.nextInt(board.getColSize()),
				RandomUtils.nextInt(board.getRowSize()));
		return position;
	}

	public boolean containsPlayer(Player player) {
		return players.contains(player);
	}

	@Transient
	public int getNumberOfPlayers() {
		return players.size();
	}

    @OneToMany
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

    @OneToOne
	public GameBoard getBoard() {
    	if (this.board == null) {
    		BoardConfiguration boardConfiguration = getConfiguration().getBoardConfiguration();
    		board = GameBoard.generateRandomBoard(boardConfiguration);
    	}
		return board;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}
    @OneToOne
	public GameConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GameConfiguration configuration) {
		this.configuration = configuration;
	}
}
