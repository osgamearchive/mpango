package net.sf.mpango.directory.dao;

import net.sf.mpango.common.dao.NotFoundException;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final String message, final Exception causedBy) {
        super(message, causedBy);
    }

    public UserNotFoundException(final Exception causedBy) {
        super(causedBy);
    }

    public UserNotFoundException() {}
}