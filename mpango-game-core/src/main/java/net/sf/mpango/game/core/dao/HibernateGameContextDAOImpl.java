package net.sf.mpango.game.core.dao;

import java.util.List;

import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.game.core.entity.GameContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateGameContextDAOImpl implements IGameContextDAO {
	
	private HibernateTemplate hibernateTemplate;

	@Override
	public GameContext save(final GameContext context) {
		final Long identifier = (Long) hibernateTemplate.save(context);
		context.setId(identifier);
		return context;
	}

	@Override
	public void update(final GameContext context) {
		hibernateTemplate.update(context);
	}

    @Override
    public void delete(final GameContext entity) throws NotFoundException {
        hibernateTemplate.delete(entity);
    }

    @Override
	public GameContext load(Long identifier) {
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
