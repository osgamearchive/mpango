package net.sf.mpango.common.entity;

import javax.persistence.Entity;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Entity
public class Name extends AbstractEntity<Long> {

    private String locale;
    private String localizedName;

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(final String localizedName) {
        this.localizedName = localizedName;
    }
}
