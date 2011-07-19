package net.sf.mpango.game.core.facade;

import net.sf.mpango.common.directory.dto.UserDTO;

/**
 * @author etux
 */
public interface IGameFacade {

   /**
     * Method that allows a User to join a game with a particular player name.
     * @param name Name of the player.
     * @param user User behind the player.
     */
    void join(String name, UserDTO user);

}
