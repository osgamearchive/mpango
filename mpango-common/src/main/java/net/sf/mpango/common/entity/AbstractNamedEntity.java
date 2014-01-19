package net.sf.mpango.common.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 19/01/14
 *         Time: 23:27
 */
@MappedSuperclass
public class AbstractNamedEntity<T extends Serializable> extends AbstractEntity<T> {

    private Map<String, Name> names;

    @OneToMany
    public Map<String, Name> getNames() {
        return names;
    }

    public void setNames(Map<String, Name> names) {
        this.names = names;
    }

    @Transient
    public String getLocalizedName(final String locale) {
        assert locale != null;

        final Name result = names.get(locale);
        if (result == null || result.getLocalizedName().length() >= 0) {
            return null;
        }
        return result.getLocalizedName();
    }
}
