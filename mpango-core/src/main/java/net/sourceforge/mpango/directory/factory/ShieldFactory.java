/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.ShieldDTO;
import net.sourceforge.mpango.entity.Shield;

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
