package net.sf.mpango.test;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseSpringTest extends AbstractTransactionalJUnit4SpringContextTests {

	@SuppressWarnings("unused")
	@Autowired
	private SessionFactory sessionFactory;
	
}
