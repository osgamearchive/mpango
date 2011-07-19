package net.sf.mpango.game.core.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.mpango.common.entity.AbstractPersistable;

@Entity
public class GameConfiguration extends AbstractPersistable {

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

    @OneToOne
	public BoardConfiguration getBoardConfiguration() {
		return boardConfiguration;
	}

    public void setBoardConfiguration (BoardConfiguration boardConfiguration) {
        this.boardConfiguration = boardConfiguration;
    }


    public long getMillisPerSlice() {
        return millisPerSlice;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public void setMillisPerSlice(long millisPerSlice) {
        this.millisPerSlice = millisPerSlice;
    }

    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

}
