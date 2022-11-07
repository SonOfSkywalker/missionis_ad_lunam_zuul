package pkg_Game.pkg_GameEngine.pkg_Room;
import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;
/**
 * Implémentation de la classe Door afin d'utiliser des locked doors.
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public class Door
{
    private boolean aIsLocked;
    private String aKeyName;

    /**
     * Constructeur naturel
     * @param pIsLocked État de verrouillage de la porte
     * @param pKeyName Nom de l'Item pouvant déverouiller la porte
     */
    public Door(final boolean pIsLocked, final String pKeyName) {
        this.aIsLocked = pIsLocked;
        this.aKeyName = pKeyName;
    }

    /**
     * @return true si la porte est verrouillée, false sinon
     */
    public boolean isLocked(){
        return this.aIsLocked;
    }

    /**
     * @return le nom de l'Item pouvant déverrouiller la porte
     */
    public String getKeyName(){
        return this.aKeyName;
    }

    /**
     * Déverrouille la porte
     */
    public void unlockDoor(){
        this.aIsLocked = false;
    }
}
