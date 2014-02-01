package net.sf.mpango.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Entity
@Table(name="LOCALES")
public class Locale extends AbstractEntity<String> {

    public static final Locale ES_ES = new Locale("es_ES", "Espana");
    public static final Locale EN_US= new Locale("en_US", "United States of America");
    public static final Locale FR_FR = new Locale("fr_FR", "France");
    public static final Locale DE_DE = new Locale("de_DE", "Deutschland");

    private String description;

    public Locale() {}

    public Locale(String code, String description) {
        this.setId(code);
        this.setDescription(description);
    }

    @Column(name = "DESCRIPTION", length = 8)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Transient
    public String getCode() {
        return this.getId();
    }
}