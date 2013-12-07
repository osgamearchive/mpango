package net.sf.mpango.common.aspectj;

import org.apache.log4j.Logger;
import org.aspectj.lang.Signature;

/**
 * <p>Logging aspect for the mpango application.</p>
 * @author edvera
 *
 */
public aspect LoggingAspect {
	
	private static final Logger _logger = Logger.getLogger(LoggingAspect.class);
	
	pointcut methodCall () :  call (public * net.sf.mpango..* (*)) &&
							 !call (public * net.sf.mpango..set*(*)) && 	//we don't want setter methods logged.
							 !call (public * net.sf.mpango..get(*)) &&  	//neither do we want getter methods logged.
							 !(within(LoggingAspect));					    //we definitely don't want endless recursivity.

	before() : methodCall() {
		if (_logger.isDebugEnabled()) {
			_logger.debug("thisJoinPointStaticPart.getSignature().getDeclaringTypeName(): "+thisJoinPoint.getStaticPart().getSignature().getDeclaringTypeName());
			_logger.debug("thisJoinPointStaticPart.getSignature().getDeclaringType(): "+thisJoinPoint.getStaticPart().getSignature().getDeclaringType());
			_logger.debug("thisJoinPointStaticPart.getSignature().getName(): "+thisJoinPoint.getStaticPart().getSignature().getName());
			_logger.debug("thisJoinPoint.getArgs(): "+thisJoinPoint.getArgs());
		}
		Logger logger = Logger.getLogger(getClass(thisJoinPoint.getStaticPart().getSignature()));
		logger.debug(thisJoinPoint.getStaticPart().getSignature().getName() +": ");
		if (logger.isDebugEnabled()) {
			for (Object arg : thisJoinPoint.getArgs()) {
				if (arg != null) {
					logger.debug("arg: "+arg.toString());
				}
			}
		}
	}
	
	after() : methodCall() {
		Logger logger = Logger.getLogger(getClass(thisJoinPoint.getStaticPart().getSignature()));
		logger.info(thisJoinPoint.getStaticPart().getSignature().getName()+" done");
	}

	after() throwing(Throwable ex) : methodCall() {
	    Signature signature = thisJoinPoint.getStaticPart().getSignature();
	    Logger logger = Logger.getLogger(getClass(signature));
	    logger.warn(signature.getName(), ex);
	}

	private String getClass(Signature signature) {
		return signature.getDeclaringTypeName();
	}
}