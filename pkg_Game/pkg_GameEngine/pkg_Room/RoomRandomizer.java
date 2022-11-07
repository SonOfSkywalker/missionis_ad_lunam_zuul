package pkg_Game.pkg_GameEngine.pkg_Room;

import java.util.Random;
import java.util.ArrayList;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;

/**
 * Classe renvoyant aléatoirement un Room du jeu.
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public abstract class RoomRandomizer
{
    /**
     * Room Randomizer
     * @param pRoomsList Liste des Rooms à tirer au sol
     * @return une Room au hasard
     */
    public static Room findRandomRoom(final ArrayList<Room> pRoomsList) {
      Random vGen = new Random();
      return pRoomsList.get(vGen.nextInt(pRoomsList.size()));
    }
}
