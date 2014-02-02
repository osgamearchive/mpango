package net.sf.mpango.directory.cdi.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.sf.mpango.directory.dao.UserDAO;
import net.sf.mpango.directory.entity.User;
import net.sf.mpango.directory.service.AuthenticationException;
import net.sf.mpango.directory.service.IAuthenticationService;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
@Stateless
public class AuthenticationService implements IAuthenticationService {

    @Inject
    private UserDAO userDAO;

    @Override
    public User login(final String email, final String password) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User register(final User user) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> list() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String generateResetKey(final String email) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUserByResetKey(final String resetKey) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void changeUserPassword(final User user, final String newPassword) throws AuthenticationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
