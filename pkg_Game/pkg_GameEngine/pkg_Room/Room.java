package pkg_Game.pkg_GameEngine.pkg_Room;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;

/**
 * Classe Room servant à créer des salles
 * @author Raphaël CANIN
 * @version 2020.10.10
 */
public class Room
{
    // ### Attributs ###
    private String aDescription;
    private HashMap<String,Room> aExits;
    private HashMap<String,Item> aItems = new HashMap<String,Item>() ;
    private ItemList aItemListRoom = new ItemList();
    private HashMap<String,Door> aDoors = new HashMap<String,Door>();
    private boolean aHereIsWalter = false;

    //D'après une idée de Baudouin Cortes, création de UNKNOWN_DIRECTION
    public static final Room UNKNOWN_DIRECTION = new Room("Unknown Direction");

    //Création d'une HashMap contenant toutes les directions possibles
    private static HashMap<String,String> sDirections = new HashMap<String,String>();

    //Image
    private String aImageName;

    // ### Constructeurs ###
    /**
     * Constructeur naturel
     * @param pDescription String pour initialiser aDescription
     * @param pImage String pour donner le nom de l'image souhaitée.
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String,Room>();
        this.aImageName = "Images/" + pImage;
    }//Room(.,.)

    /**
     * Constructeur sans image
     * @param pDescription String pour initialiser aDescription
     */
    public Room(final String pDescription)
    {
        this.aDescription = pDescription;
        this.aExits = new HashMap<String,Room>();
    }//Room(.)

    // ### Accesseurs ###
    /**
     * Accesseur de la description d'une Room
     * @return La description de la salle
     */
    public String getDescription() { return this.aDescription;}//getDescription()

    /**
     * Accesseur de l'image d'une Room
     * @return Le String contenant le nom de l'image
     */
    public String getImageName(){return this.aImageName;}
    // ### Autres Méthodes ####

    /**
     * Positionnement d'une sortie + Ajout de la direction entrée dans la HashMap sDirections
     * @param pS String Direction de la sortie
     * @param pR Room Pièce à affecter à cette sortie
     */
    public void setExit (final String pS, final Room pR)
    {
        if (pR != null) {
            this.sDirections.put(pS,"");
            this.aExits.put(pS,pR);
        }
    }//setExit(.,.)

    /**
     * Renvoie la Room de sortie correspondant à la direction entrée.
     * Si la sortie ne contient pas de Room, on renvoie null.
     * Si le String entré ne correspond à aucune direction connue, on renvoie Room.UNKNOWN_DIRECTION
     * @param pDirection String Direction souhaitée
     * @return Room correspondant à la sortie demandée.
     */
    public Room getExit (final String pDirection){
        if (sDirections.containsKey(pDirection)) return this.aExits.get(pDirection);
        else return Room.UNKNOWN_DIRECTION;
    }//getExit(.)

    /**
     * Renvoie un String détaillant toutes les sorties disponibles
     * @return Une description des sorties disponibles.
     */
    public String getExitString(){
        StringBuilder vExit = new StringBuilder("");
        Set<String> keys = this.aExits.keySet();

        for(String exit : keys) {
            vExit.append(exit + " ");
        }

        return vExit.toString();
    }//getExitString()

    /**
     * Renvoie la description longue d'une Room :
     *      You are in the Sea of Vapors
     *      Exits : north
     *      No item here.
     *  @return Une description de la Room, ses sorties et les items présents
     */
    public String getLongDescription(){
        String vItemsString = "";
        if (this.aItemListRoom.isEmpty()) vItemsString = "No item here.";
        else vItemsString = "Items in the room : " + this.aItemListRoom.getItemsString();
        String vWalterString = "";
        if (this.aHereIsWalter) vWalterString = "Walter is in the room. He need to be healed right now !";
        return "You are " + this.getDescription() + ".\n" + "Exits : " + this.getExitString() + "\n"  + vItemsString + "\n\n" + vWalterString + "\n";
    }//getLongDescription()

    /**
     * Permet de définir un item pour une Room
     * @param pItem item à ajouter à la Room
     */
    public void addItem(final Item pItem){
        this.aItemListRoom.addItem(pItem);
    }//setItem(.)

    /**
     * Permet de retirer un item d'une Room
     * @param pItemString item à retirer de la Room
     */
    public void removeItem(final String pItemString){
        this.aItemListRoom.removeItem(pItemString);
    }//removeItem(.)

    /**
     * Permet de récupérer l'item de la pièce
     * @param pS String contenant le nom d'un item
     * @return Renvoie l'item de la HashMap correspondant à la clé passée en paramètre, null sinon
     */
    public Item getItem(final String pS){
        return this.aItemListRoom.getItem(pS);
    }//getItem(.)

    /**
     * Informe si la Room passée en paramètre est une sortie de la salle courante
     * @param pR Room à vérifier
     * @return boolean true si la Room est une sortie, false sinon
     */
    public boolean isExit(final Room pR){
        return this.aExits.containsValue(pR);
    }

    /**
     * Positionnement d'une sortie + Ajout de la direction entrée dans la HashMap sDirections
     * @param pS Direction de la sortie
     * @param pD Door à affecter à cette sortie
     */
    public void setDoor (final String pS, final Door pD)
    {
        if (pD != null) {
            this.aDoors.put(pS,pD);
        }
    }//setDoor(.,.)

    /**
     * @param pDirection Direction à étudier
     * @return true si la direction entrée est accessible (non bloquée par une porte verouillée), false sinon
     */
    public boolean canGoThrough(final String pDirection) {
        return this.aDoors.get(pDirection)==null || (this.aDoors.get(pDirection)!=null && !this.aDoors.get(pDirection).isLocked());
    }

    /**
     * @param pDirection Direction à étudier
     * @return Le nom de l'Item pouvant ouvrir la porte
     */
    public String getKeyString(final String pDirection){
        return this.aDoors.get(pDirection).getKeyName();
    }

    /**
     * @param pDirection Direction à étudier
     * @return La Door correspond à la sortie demandée, null sinon.
     */
    public Door getExitDoor(final String pDirection){
        return this.aDoors.get(pDirection);
    }

    /**
     * Setter à true de aHereIsWalter
     */
    public void walterIsHere(){
        this.aHereIsWalter = true;
    }
    
    /**
     * Getter de aHereIsWalter
     * @return aHereIsWalter
     */
    public boolean isWalterHere() {
     return this.aHereIsWalter;
    }
} // Room
