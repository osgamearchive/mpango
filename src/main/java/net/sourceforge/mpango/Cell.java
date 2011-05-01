package net.sourceforge.mpango;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sourceforge.mpango.events.AbstractListenerBroadcaster;
import net.sourceforge.mpango.events.Listener;


/**
 * The terrain class represents the characteristics of a particular cell of the game board.
 * There will be different types of terrain with different attributes.
 * @author etux
 *
 */
@Entity
public class Cell extends AbstractListenerBroadcaster<Construction> implements Serializable {

	/** generated serial version uid */
	private static final long serialVersionUID = 2294912901732869716L;
	private Long identifier;
	private List<Construction> constructions = new ArrayList<Construction>();
	
	private float defenseBonus;
	private float attackBonus;
	private int column;
	private int row;

	public Cell(int columnNumber, int rowNumber) {
		this.setColumn(columnNumber);
		this.setRow(rowNumber);
	}
	
	public float calculateDefenseBonus () {
		return defenseBonus + calculateConstructionDefenseBonus ();
	}

	public float calculateAttackBonus () {
		return attackBonus + calculateConstructionAttackBonus ();
	}

	public float calculateConstructionDefenseBonus () {
		float totalBonus = 0;
		for (Construction construction : constructions) {
			totalBonus += construction.getDefenseBonus ();
		}
		return totalBonus;
	}
	public float calculateConstructionAttackBonus () {
		float totalBonus = 0;
		for (Construction construction : constructions) {
			totalBonus += construction.getAttackBonus ();
		}
		return totalBonus;
	}
	@OneToMany
	public List<Construction> getConstructions() {
		return constructions;
	}
	public void setConstructions(List<Construction> constructions) {
		this.constructions = constructions;
	}
	@Transient
	public List<Listener> getListeners() {
		return obtainListenerList(this.constructions);
	}
	public void addConstruction (Construction construction) throws ConstructionAlreadyInPlaceException {
		if (this.constructions.contains (construction)) {
			throw new ConstructionAlreadyInPlaceException (construction);
		}
		this.constructions.add(construction);
	}
	public void removeConstruction (Construction construction) throws ConstructionNotFoundException {
		if (!this.constructions.contains (construction)) {
			throw new ConstructionNotFoundException (construction);
		}
		this.constructions.remove (construction);
	}
	@Id
	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	@Column
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	@Column
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}

	public float getDefenseBonus() {
		return defenseBonus;
	}

	public void setDefenseBonus(float defenseBonus) {
		this.defenseBonus = defenseBonus;
	}

	public float getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(float attackBonus) {
		this.attackBonus = attackBonus;
	}
}