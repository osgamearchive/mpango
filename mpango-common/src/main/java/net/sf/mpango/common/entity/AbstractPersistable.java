package net.sf.mpango.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.sf.mpango.common.dao.Identified;

@MappedSuperclass
public abstract class AbstractPersistable<T extends Serializable> implements Identified<T>{

	private T identifier;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
	public T getId() {
		return identifier;
	}
	public void setId(T identifier) {
		this.identifier = identifier;
	}
	
}
