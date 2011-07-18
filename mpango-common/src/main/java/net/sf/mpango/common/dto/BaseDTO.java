package net.sf.mpango.common.dto;

import java.io.Serializable;

/**
 * @author aplause
 * 
 */
public abstract class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
