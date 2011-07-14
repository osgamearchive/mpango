package net.sourceforge.mpango.directory.service;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * This class is used to retrieve spring application context
 * 
 * @author devdlee
 * 
 */
public class ApplicationContextService {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring-core.xml"});
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
}
