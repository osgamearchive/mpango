package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.web.dto.PositionDTO;
import net.sf.mpango.game.core.entity.Position;

/**
 * @author aplause
 * 
 */
public class PositionFactory extends BaseFactory<PositionDTO, Position> {

	private PositionFactory() {
		super();
	}

	public static PositionFactory instance() {
		return new PositionFactory();
	}

	@Override
	public Position create(PositionDTO dto) {
		Position position = new Position(dto.getRowNumber(), dto.getColNumber());
		return position;
	}

}
