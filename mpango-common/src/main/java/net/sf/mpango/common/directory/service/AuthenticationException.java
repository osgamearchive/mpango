package net.sf.mpango.common.directory.service;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(Exception causedBy) {
        super(causedBy);
    }

    public AuthenticationException(final String message) {
        super(message);
    }

    public AuthenticationException (final String message, final Exception causedBy) {
        super(message, causedBy);
    }
}
