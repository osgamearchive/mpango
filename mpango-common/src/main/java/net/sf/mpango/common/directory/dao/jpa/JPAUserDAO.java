package net.sf.mpango.common.directory.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import net.sf.mpango.common.dao.JPAAbstractDAO;
import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.dao.UserNotFoundException;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.qualifiers.CDIBased;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@CDIBased
public class JPAUserDAO extends JPAAbstractDAO<User, Long> implements UserDAO {

    @PersistenceUnit(name = "common-persistence-unit")
    private EntityManager entityManager;

    public JPAUserDAO() {
        super(User.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public String getListNamedQueryName() {
        return User.NAMED_QUERY_LIST_ALL_USERS;
    }

    @Override
    public User loadByEmail(final String email) throws UserNotFoundException {
        final Query query = getEntityManager().createNamedQuery(User.NAMED_QUERY_FIND_USER_BY_EMAIL);
        query.setParameter(0, email);
        return (User) query.getSingleResult();
    }

    @Override
    public User lookUpByResetKey(String resetKey) throws UserNotFoundException {
        final Query query = getEntityManager().createNamedQuery(User.NAMED_QUERY_FIND_USER_BY_RESET_KEY);
        query.setParameter(0, resetKey);
        Object result = query.getSingleResult();
        if (result == null) {
            throw new UserNotFoundException();
        }
        return (User) query.getSingleResult();
    }

    /**
     * For testing purposes only
     * @param entityManager
     */
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
