/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.dto.WeaponDTO;
import net.sourceforge.mpango.entity.Weapon;

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
