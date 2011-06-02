package net.sourceforge.mpango.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sourceforge.mpango.events.AbstractListenerBroadcaster;

@Entity
public class Row extends AbstractListenerBroadcaster<Cell> implements Serializable {

	/** generated serial version uid */
	private static final long serialVersionUID = -7742236593357018492L;
	private Long identifier;
	private List<Cell> cells;
	private int size;
	private int position;
	
	/**
	 * Constructor used to create a row from scratch
	 * @param cellNumber
	 */
	public Row (int position, int size) {
		this.setPosition(position);
		this.setSize(size);
		cells = new ArrayList<Cell>(size);
		for (int i=0; i<size; i++) {
			Cell cell = new Cell(position, i);
			cells.add(cell);
		}
	}
	
	/**
	 * default constructor
	 */
	public Row() {
		super();
	}
	
	@Id
	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	@Column
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Column(name="Pos") //Custom naming needed due to predefined name (POSITION)
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	@OneToMany
	public List<Cell> getCells() {
		return cells;
	}
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	public Cell getCell (int position) {
		if (position >= size) {
			throw new IllegalArgumentException();
		}
		return cells.get(position);
	}
	@Override
	@Transient
	protected List<Cell> getListeners() {
		return obtainListenerList(this.cells);
	}
}
