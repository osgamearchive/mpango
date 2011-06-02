package net.sourceforge.mpango.entity;

import java.util.ArrayList;
import java.util.List;

public class GameContext {

	protected List<Player> players;
	protected GameBoard board;
	protected GameConfiguration configuration;
	
	private GameContext(BoardConfiguration boardConfiguration) {
		players = new ArrayList<Player>();
		board = new GameBoard(boardConfiguration);
	}
	
	public GameContext(GameConfiguration configuration) {
		this(configuration.getBoard());
		this.configuration = configuration;
		
	}

	public void join(Player player) {
		players.add(player);
	}

	public boolean containsPlayer(Player player) {
		return players.contains(player);
	}

}
