package net.sf.mpango.game.core.entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.mpango.common.entity.AbstractEntity;
import net.sf.mpango.game.core.enums.Resources;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.events.Observable;
import net.sf.mpango.game.core.events.ObservableBaseListener;
import net.sf.mpango.game.core.exception.ConstructionAlreadyInPlaceException;
import net.sf.mpango.game.core.exception.ConstructionNotFoundException;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.core.terrains.Terrain;
import org.hibernate.annotations.CollectionOfElements;


/**
 * The terrain class represents the characteristics of a particular cell of the game board.
 * There will be different types of terrain with different attributes.
 * @author etux
 *
 */
@Entity
public class Cell extends AbstractEntity<Long> implements Observable,Listener {

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
	private int altitude;
	private Terrain terrain;

    private ObservableBaseListener observableBaseListener;

    public Cell() {
        observableBaseListener = new ObservableBaseListener(this);
    }

    public Cell(final int rowPosition, final int colPosition, final Set<Resources> resources) {
        this();
        this.column = colPosition;
        this.row = rowPosition;
        this.resources = resources;
        this.constructions = new LinkedList<Construction>();
    }
    public Cell(int rowPosition, int colPosition) {
        this(rowPosition, colPosition, new HashSet<Resources>());
	}

	public void addResource(Resources resource) {
		resources.add(resource);
	}

    @CollectionOfElements
    @Enumerated(EnumType.ORDINAL)
	public Set<Resources> getResources() {
		return resources;
	}

    public void setResources(Set<Resources> resources) {
        this.resources = resources;
    }

    @Transient
	public boolean hasCity() {
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
		this.constructions.remove(construction);
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
    
	public int getAltitude() {
		return altitude;
	}
	
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}
	
    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    @Override
    public void receive(Event event) throws EventNotSupportedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void notifyListeners(Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addListener(Listener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeListener(Listener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}