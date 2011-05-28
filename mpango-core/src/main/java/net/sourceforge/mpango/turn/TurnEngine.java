package net.sourceforge.mpango.turn;

public class TurnEngine {

	private Long currentTurn;
	
	public TurnEngine() {
		currentTurn = 0l;
	}
	
	public void passTurn() {
		currentTurn++;
	}
	
	public Long getCurrentTurn() {
		return currentTurn;
	}

}
