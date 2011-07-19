package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.entity.BaseFactory;
import net.sf.mpango.game.core.dto.PositionDTO;
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
