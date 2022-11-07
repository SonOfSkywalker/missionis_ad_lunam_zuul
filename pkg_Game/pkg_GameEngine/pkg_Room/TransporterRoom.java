package pkg_Game.pkg_GameEngine.pkg_Room;

import java.util.ArrayList;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;

/**
 * Sous-classe de Room implémentant la Transporter Room
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public class TransporterRoom extends Room
{
    private ArrayList<Room> aRoomsList;
    private Room aRoomTestMode;

    /**
     * Constructeur naturel
     * @param pDescription String pour initialiser aDescription
     * @param pImage String pour donner le nom de l'image souhaitée.
     * @param pRoomsList Liste des Rooms à tirer au sort
     */
    public TransporterRoom(final String pDescription, final String pImage, final ArrayList<Room> pRoomsList)
    {
        super(pDescription, pImage);
        this.aRoomsList = pRoomsList;
    }//Room(.,.)

    /**
     * Renvoie la Room de sortie aléatoire
     * @param pDirection Non important
     * @return Room correspondant à la sortie demandée.
     */
    @Override public Room getExit (final String pDirection){
        if (this.aRoomTestMode != null) return this.aRoomTestMode;
        return RoomRandomizer.findRandomRoom(this.aRoomsList);
    }//getExit(.)

    /**
     * Setter de aRoomNameTestMode
     * @param pRoom Room à laquelle initialiser l'attribut
     */
    public void setRoomTestMode(final Room pRoom){
        this.aRoomTestMode = pRoom;
    }
}
