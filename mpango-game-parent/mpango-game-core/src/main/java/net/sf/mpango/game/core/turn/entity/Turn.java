package net.sf.mpango.game.core.turn.entity;

import java.util.Date;

public class Turn {

	private long id;
	private long turnNumber;
	private Date turnStarted;
	private Date turnFinished;
	
	public Turn(final long i, final Date dateStarted) {
		this.turnNumber = i;
		this.turnStarted = dateStarted;
	}
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getTurnNumber() {
		return turnNumber;
	}
	public void setTurnNumber(Long turnNumber) {
		this.turnNumber = turnNumber;
	}
	public Date getTurnStarted() {
		return turnStarted;
	}
	public void setTurnStarted(Date turnStarted) {
		this.turnStarted = turnStarted;
	}
	public Date getTurnFinished() {
		return turnFinished;
	}
	public void setTurnFinished(Date turnFinished) {
		this.turnFinished = turnFinished;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) id;
		result = prime * result + (int) turnNumber;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turn other = (Turn) obj;
        if (id != other.getId()) {
            return false;
        }
		return true;
	}
	
	
}
