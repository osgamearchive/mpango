package net.sf.mpango.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractNamedEntity<T extends Serializable> extends AbstractEntity<T> {

    private Map<Locale, Name> names;

    public AbstractNamedEntity() {
        names = new HashMap<>();
    }

    @OneToMany(cascade= CascadeType.ALL)
    @MapKey(name = "locale")
    public Map<Locale, Name> getNames() {
        return names;
    }

    public void setNames(Map<Locale, Name> names) {
        this.names = names;
    }

    public void addLocalizedMessage (final Locale locale, final String message) {
        Name name = new Name(locale, message);
        this.names.put(locale, name);
    }

    public void removeLocalizedMessage (final Locale locale) {
        this.names.remove(locale);
    }

    @Transient
    public String getLocalizedName(final Locale locale) {
        assert locale != null;

        final Name result = names.get(locale);
        if (result == null || result.getLocalizedName().length() <= 0) {
            return null;
        }
        return result.getLocalizedName();
    }
}
