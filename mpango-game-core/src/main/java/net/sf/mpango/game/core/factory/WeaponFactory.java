/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.mpango.game.core.factory;

import net.sf.mpango.common.entity.BaseFactory;
import net.sf.mpango.game.core.dto.WeaponDTO;
import net.sf.mpango.game.core.entity.Weapon;

/**
 *
 * @author randolph
 */
public class WeaponFactory extends BaseFactory<WeaponDTO, Weapon>{
    
    private WeaponFactory() {
        super();
    }

    /**
     * return an instance of WeaponFactory
     * 
     * @return 
     */
    public static WeaponFactory instance() {
       return new WeaponFactory(); 
    }
    
    
    @Override
    public Weapon create(WeaponDTO dto) {
        return new Weapon(dto.getAttackBonus());                
    }
    
}
