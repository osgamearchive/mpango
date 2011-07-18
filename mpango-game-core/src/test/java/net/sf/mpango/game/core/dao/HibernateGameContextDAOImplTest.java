package net.sf.mpango.game.core.dao;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import net.sf.mpango.game.core.entity.GameContext;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateGameContextDAOImplTest {

	private HibernateTemplate hibernateTemplate;
	private HibernateGameContextDAOImpl testing;
	
	@Before
	public void setUp() {
		testing = new HibernateGameContextDAOImpl();
		hibernateTemplate = EasyMock.createMock(HibernateTemplate.class);
		testing.setHibernateTemplate(hibernateTemplate);
	}
	
	@Test
	public void testSave() {
		GameContext context = new GameContext();
		EasyMock.expect(hibernateTemplate.save(context)).andReturn(new Long(1));
		EasyMock.replay(hibernateTemplate);
		GameContext returnedContext = testing.save(context);
		Assert.assertEquals(new Long(1), returnedContext.getIdentifier());
		Assert.assertEquals(context, returnedContext);
		EasyMock.verify(hibernateTemplate);
	}
	
	@Test
	public void testUpdate() {
		GameContext context = new GameContext();
		hibernateTemplate.update(context);
		EasyMock.replay(hibernateTemplate);
		testing.update(context);
		EasyMock.verify(hibernateTemplate);
	}
	
	@Test
	public void testLoad() {
		Long identifier = new Long(1);
		EasyMock.expect(hibernateTemplate.load(GameContext.class, identifier)).andReturn(new GameContext());
		EasyMock.replay(hibernateTemplate);
		GameContext context = testing.load(identifier);
		Assert.assertNotNull(context);
		EasyMock.verify(hibernateTemplate);
	}
	
	@Test
	public void testList() {
		EasyMock.expect(hibernateTemplate.find("from GameContext")).andReturn(Arrays.asList(new GameContext()));
		EasyMock.replay(hibernateTemplate);
		List<GameContext> contexts = testing.list();
		Assert.assertNotNull(contexts);
		EasyMock.verify(hibernateTemplate);
	}
}