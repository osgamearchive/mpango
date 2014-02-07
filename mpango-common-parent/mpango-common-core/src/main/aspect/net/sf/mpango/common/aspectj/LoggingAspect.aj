package net.sf.mpango.common.aspectj;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;

/**
 * <p>Logging aspect for the mpango application.</p>
 * @author edvera
 *
 */
public aspect LoggingAspect {
	
	private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());
	
	pointcut methodCall () :  call (public * net.sf.mpango..* (*)) &&
							 !call (public * net.sf.mpango..set*(*)) && 	//we don't want setter methods logged.
							 !call (public * net.sf.mpango..get(*)) &&  	//neither do we want getter methods logged.
							 !(within(LoggingAspect));					    //we definitely don't want endless recursivity.

	before() : methodCall() {
        LOGGER.log(Level.FINEST, "thisJoinPointStaticPart.getSignature().getDeclaringTypeName(): {0}", thisJoinPoint.getStaticPart().getSignature().getDeclaringTypeName());
        LOGGER.log(Level.FINEST, "thisJoinPointStaticPart.getSignature().getDeclaringType(): {0}", thisJoinPoint.getStaticPart().getSignature().getDeclaringType());
        LOGGER.log(Level.FINEST, "thisJoinPointStaticPart.getSignature().getName(): {0}", thisJoinPoint.getStaticPart().getSignature().getName());
        LOGGER.log(Level.FINEST, "thisJoinPoint.getArgs(): {0}", thisJoinPoint.getArgs());

		log(thisJoinPoint, Level.FINEST, "Method called {0}, with arguments", thisJoinPoint.getStaticPart().getSignature().getName());
        for (final Object arg : thisJoinPoint.getArgs()) {
            if (arg != null) {
                log(thisJoinPoint, Level.FINEST, "arg: {0}", arg.toString());
            }
        }
	}
	
	after() : methodCall() {
        log(thisJoinPoint, Level.INFO, "{0} finished", thisJoinPoint.getStaticPart().getSignature().getName());
	}

	after() throwing(Throwable ex) : methodCall() {
        log(thisJoinPoint, Level.WARNING, "Exception {0} thrown: {1}", ex.getClass(), ex.getMessage());
	}

    private void log(final JoinPoint joinPoint, final Level level, final Object... arguments) {
        log(joinPoint, level, joinPoint.getStaticPart().getSignature().getName(), arguments);
    }

    private void log(final JoinPoint joinPoint, final Level level, final String message, final Object... arguments) {
        getLogger(joinPoint).log(level, message, arguments);
    }

    private Logger getLogger(final JoinPoint joinPoint) {
        return Logger.getLogger(joinPoint.getStaticPart().getSignature().getDeclaringTypeName());
    }
}