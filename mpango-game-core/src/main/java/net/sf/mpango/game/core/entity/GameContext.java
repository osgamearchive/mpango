package net.sf.mpango.game.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang.math.RandomUtils;

@Entity
public class GameContext extends AbstractPersistable {

	protected List<Player> players = new ArrayList<Player>();
	protected GameBoard board;
	protected GameConfiguration configuration;

	public void join(Player player) {
		player.setPosition(generateRandomPosition());
		player.setUnits(generateStartingUnits());
		players.add(player);
	}

	private List<Unit> generateStartingUnits() {
		List<Unit> units = new ArrayList<Unit>();
        units.add(new Unit());
		return units;
	}

	private Position generateRandomPosition() {
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
