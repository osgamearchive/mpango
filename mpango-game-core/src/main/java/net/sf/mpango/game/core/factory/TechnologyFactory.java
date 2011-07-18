/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.core.dto.TechnologyDTO;
import net.sf.mpango.game.core.entity.Technology;

/**
 *
 * @author randolph
 */
public class TechnologyFactory extends BaseFactory<TechnologyDTO, Technology> {
    
    private TechnologyFactory() {
        super();
    }
    
    public static TechnologyFactory instance() {
        return new TechnologyFactory();
    }

    @Override
    public Technology create(TechnologyDTO dto) {
        Technology tech = new Technology();
        tech.setIdentifier(dto.getId());
        //TODO check if its correct!!!
        tech.setRequiredTechnologies(TechnologyFactory.instance().createList(dto.getRequiredTechnologies()));
        tech.setTechnologyCost(dto.getTechnologyCost());
        return tech;
    }
    
}
