package net.sourceforge.mpango.service;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.exception.CommandException;

import javax.sound.midi.VoiceStatus;

/**
 * <p>Game Service that takes care of the interactions coming from the clients.</p>
 * @author edvera
 */
public interface IGameService {

    /**
     * Method that allows a User to join a game with a particular player name.
     * @param name Name of the player.
     * @param user User behind the player.
     */
    void join(String name, User user);

    /**
     * Method that allows a User to settle a unit in a particular cell creating a city.
     * @param unit Unit to settle.
     * @param cell Cell on which to build the city.
     * @throws CommandException in case it was impossible to execute the action.
     */
    void settleUnit(Unit unit, Cell cell) throws CommandException;
}
