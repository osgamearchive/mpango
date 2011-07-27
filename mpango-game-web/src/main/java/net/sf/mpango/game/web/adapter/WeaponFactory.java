/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.mpango.game.web.adapter;

import net.sf.mpango.common.factory.BaseFactory;
import net.sf.mpango.game.web.dto.WeaponDTO;
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
