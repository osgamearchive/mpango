package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.PositionDTO;
import net.sourceforge.mpango.entity.Position;

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
