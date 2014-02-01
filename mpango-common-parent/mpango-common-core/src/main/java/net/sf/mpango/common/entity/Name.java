package net.sf.mpango.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Entity
public class Name extends AbstractEntity<Long> {

    private Locale locale;
    private String localizedName;

    public Name() {}

    public Name(final Locale locale, final String localizedName) {
        assert locale != null;
        assert localizedName != null;

        this.locale = locale;
        this.localizedName = localizedName;
    }

    @ManyToOne
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    @Column(name = "LOCALIZED_NAME")
    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(final String localizedName) {
        this.localizedName = localizedName;
    }
}
