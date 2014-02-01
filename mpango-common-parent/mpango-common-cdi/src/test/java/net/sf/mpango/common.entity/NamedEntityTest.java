package net.sf.mpango.common.entity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Table;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class NamedEntityTest {

    protected static final String SPANISH_HELLO_WORLD = "Hola mundo";
    protected static final String ENGLISH_HELLO_WORLD = "Hello world";
    protected static final String FRENCH_HELLO_WORLD = "Bonjour tout le monde";
    protected static final String GERMAN_HELLO_WORLD = "Hallo Welt";
    private NamedEntity namedEntity = null;
    private long id;

    /////////////////////////////////////////////////////////////////////////////
    // Java Persistence related objects                                        //
    /////////////////////////////////////////////////////////////////////////////
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("common-persistence-unit");
    private EntityManager entityManager;

    @Before
    public void setUp() {
        namedEntity = new NamedEntity();
        addMessages(namedEntity);

        entityManager = entityManagerFactory.createEntityManager();
    }

    private void addMessages(final NamedEntity namedEntity) {
        namedEntity.addLocalizedMessage(Locale.ES_ES, SPANISH_HELLO_WORLD);
        namedEntity.addLocalizedMessage(Locale.EN_US, ENGLISH_HELLO_WORLD);
        namedEntity.addLocalizedMessage(Locale.FR_FR, FRENCH_HELLO_WORLD);
        namedEntity.addLocalizedMessage(Locale.DE_DE, GERMAN_HELLO_WORLD);
    }


    @Test
    public void readLocalizedMessages() {
        assertMessage(Locale.DE_DE, GERMAN_HELLO_WORLD);
        assertMessage(Locale.EN_US, ENGLISH_HELLO_WORLD);
        assertMessage(Locale.ES_ES, SPANISH_HELLO_WORLD);
        assertMessage(Locale.FR_FR, FRENCH_HELLO_WORLD);
    }

    @Test
    public void persist() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(namedEntity);
        transaction.commit();

        assertThat(namedEntity.getId(), is(notNullValue()));
    }

    private void assertMessage(final Locale locale, final String message) {
        assertThat("The message should be the expected one", message, is(equalTo(namedEntity.getLocalizedName(locale))));
    }


    @Entity
    @Table(name = "NAMED_ENTITIES")
    public static class NamedEntity extends AbstractNamedEntity<Long> {}

}
