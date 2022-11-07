package pkg_Game.pkg_GameEngine.pkg_Room;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;
/**
 * Sous-classe de Door représentant les portes à énigme
 *
 * @author Raphaël CANIN
 * @version 2021
 */
public class RiddleDoor extends Door
{
     /**
     * Constructeur naturel
     * @param pIsLocked État de verrouillage de la porte
     * @param pKeyName Nom de l'Item pouvant déverrouiller la porte
     */
    public RiddleDoor(final boolean pIsLocked, final String pKeyName) {
        super(pIsLocked, pKeyName);
    }
}
