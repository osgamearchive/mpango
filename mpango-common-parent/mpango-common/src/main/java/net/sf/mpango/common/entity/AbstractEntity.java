package net.sf.mpango.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import net.sf.mpango.common.dao.Identified;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity<T extends Serializable> implements Identified<T>{

	private T identifier;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public T getId() {
		return identifier;
	}
	public void setId(T identifier) {
		this.identifier = identifier;
	}
	
}
