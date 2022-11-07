package pkg_Game.pkg_GameEngine.pkg_Item;
import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;

/**
 * Implémente le fonctionnement du beamer (exercice 7.44)
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public class Beamer extends Item
{
    private Room aChargedRoom = null;

    /**
     * Constructeur unique de la classe Beamer
     */
    public Beamer(){
        super("beamer",400, "a beamer", " A beamer is a device that can be charged, and fired. When you charge the beamer, it memorizes the current room. When you fire the beamer, it transports you immediately back to the room it was charged in.");
    }//Beamer()

    /**
     * Indique si le beamer est chargé ou non
     * @return true si le beamer est chargé, false sinon.
     */
    public boolean isCharged(){
        return (this.aChargedRoom != null);
    }//isCharged()

    /**
     * Permet de charger le beamer
     * @param pRoom Room à charger
     */
    public void chargeRoom(final Room pRoom){
        this.aChargedRoom = pRoom;
    }//chargeBeamer(.)

    /**
     * Permet de décharger le beamer
     */
    public void unchargeRoom(){
        this.aChargedRoom = null;
    }//unchargeBeamer()

    /**
     * Renvoie la Room stockée dans le beamer
     * @return la Room stockée dans le beamer
     */
    public Room fireRoom(){
        return this.aChargedRoom;
    }//fireBeamer()
}
