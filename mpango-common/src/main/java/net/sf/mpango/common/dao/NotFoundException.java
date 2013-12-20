package net.sf.mpango.common.dao;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 15/12/13
 *         Time: 22:07
 */
public class NotFoundException extends Exception {

    public NotFoundException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public NotFoundException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public NotFoundException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
