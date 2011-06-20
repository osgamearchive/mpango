package net.sourceforge.mpango.entity;

public class GameConfiguration {

    private static final long DEFAULT_MILLIS_PER_SLICE = 100l;
    private static final int DEFAULT_MAX_NUMBER_OF_PLAYERS = 10;

    private long millisPerSlice;
    private int maxNumberOfPlayers;
	private BoardConfiguration boardConfiguration;

    public GameConfiguration (BoardConfiguration configuration) {
        this.millisPerSlice = DEFAULT_MILLIS_PER_SLICE;
        this.maxNumberOfPlayers = DEFAULT_MAX_NUMBER_OF_PLAYERS;
        this.boardConfiguration = configuration;
    }

	public BoardConfiguration getBoardConfiguration() {
		return boardConfiguration;
	}

    public long getMillisPerSlice() {
        return millisPerSlice;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

}
