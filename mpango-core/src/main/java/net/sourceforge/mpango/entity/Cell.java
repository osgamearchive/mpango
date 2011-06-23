package net.sourceforge.mpango.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import net.sourceforge.mpango.enums.Resources;
import net.sourceforge.mpango.events.AbstractListenerObservable;
import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.ConstructionAlreadyInPlaceException;
import net.sourceforge.mpango.exception.ConstructionNotFoundException;
import net.sourceforge.mpango.exception.EventNotSupportedException;


/**
 * The terrain class represents the characteristics of a particular cell of the game board.
 * There will be different types of terrain with different attributes.
 * @author etux
 *
 */
@Entity
public class Cell extends AbstractListenerObservable implements Serializable {

	/** generated serial version uid */
	private static final long serialVersionUID = 2294912901732869716L;
	private Long identifier;
	private List<Construction> constructions;
	private Set<Resources> resources;
	private boolean hasCity;
	private float defenseBonus;
	private float attackBonus;
	private int column;
	private int row;
	
	public Cell(int rowPosition, int colPosition) {
		this(rowPosition, colPosition, new HashSet<Resources>());
	}
	public Cell(int rowPosition, int colPosition, Set<Resources> resources) {
		super(new Listener() {
			@Override
			public void receive(Event event) throws EventNotSupportedException {}
		});
		this.column = colPosition;
		this.row = rowPosition;
		this.resources = resources;
        this.constructions = new ArrayList<Construction>();
	}
	
	public void addResource(Resources resource) {
		resources.add(resource);
	}

    @OneToMany
	public Set<Resources> getResources() {
		return resources;
	}

    public void setResources(Set<Resources> resources) {
        this.resources = resources;
    }

    @Transient
	public boolean isHasCity() {
		return hasCity;
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
	
	public boolean containsConstruction(Construction construction) {
		return this.constructions.contains(construction);
	}
	public void addConstruction (Construction construction) throws ConstructionAlreadyInPlaceException {
		if (this.containsConstruction (construction)) {
			throw new ConstructionAlreadyInPlaceException (construction);
		}
		if (construction instanceof City) {
			hasCity = true;
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
	
	@Override
	public void notifyListeners(Event event) {
		// TODO Auto-generated method stub
		
	}

    @Transient
	public boolean isCollectable() {
		// TODO Auto-generated method stub
		return false;
	}

    @Transient
	public int getCollectionPoints() {
		// TODO Auto-generated method stub
		return 0;
	}
}