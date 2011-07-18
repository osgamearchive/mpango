package net.sf.mpango.game.core.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import net.sf.mpango.game.core.entity.GameContext;

public class HibernateGameContextDAOImpl implements IGameContextDAO {
	
	private HibernateTemplate hibernateTemplate;

	@Override
	public GameContext save(GameContext context) {
		Long identifier = (Long) hibernateTemplate.save(context);
		context.setIdentifier(identifier);
		return context;
	}

	@Override
	public void update(GameContext context) {
		hibernateTemplate.update(context);
	}

	@Override
	public GameContext load(Serializable identifier) {
		return hibernateTemplate.load(GameContext.class, identifier);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameContext> list() {
		return hibernateTemplate.find("from GameContext");
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
