package net.sf.mpango.game.core.entity;

import javax.persistence.Entity;

@Entity
public class Official extends Unit {

    public Official() {
        super();
    }

	public Official(City city) {
		super(city);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8183154707171765596L;

}
