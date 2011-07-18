package net.sf.mpango.game.core.turn.entity;

import java.util.Date;

public class Turn {

	private Long id;
	private Long turnNumber;
	private Date turnStarted;
	private Date turnFinished;
	
	public Turn(long i, Date dateStarted) {
		this.turnNumber = i;
		this.turnStarted = dateStarted;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTurnNumber() {
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((turnNumber == null) ? 0 : turnNumber.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (turnNumber == null) {
			if (other.turnNumber != null)
				return false;
		} else if (!turnNumber.equals(other.turnNumber))
			return false;
		return true;
	}
	
	
}
