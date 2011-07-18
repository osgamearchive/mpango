package net.sf.mpango.game.core.enums;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Enumeration of resources of the game.
 * @author etux
 *
 */
@Entity
public enum Resources {

	FOOD(0);

    private Integer id;

    private Resources(Integer id) {
        this.id = id;
    }
    @Id
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
