/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.web.dto.ShieldDTO;
import net.sf.mpango.game.core.entity.Shield;

/**
 *
 * @author randolph
 */
public class ShieldFactory extends BaseFactory<ShieldDTO, Shield> {
    
    private ShieldFactory() {
        super();
    }
    
    /**
     * Returns new instance of Shield
     * 
     * @return 
     */
    public static ShieldFactory instance() {
        return new ShieldFactory();
    }
    
    @Override
    public Shield create(ShieldDTO dto) {
        Shield shield = new Shield(dto.getMaximumHitPoints());
        return shield;
    }
    
}
