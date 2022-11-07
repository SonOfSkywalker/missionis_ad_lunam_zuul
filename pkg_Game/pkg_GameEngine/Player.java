package pkg_Game.pkg_GameEngine;

import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;

import java.util.HashMap;
import java.util.Stack;
/**
 * Classe permettant la création de joueurs dans le jeu.
 * Elle contient les méthodes propres au joueur.
 * @author Raphael CANIN
 * @version 2020
 */
public class Player
{
    private Room aCurrentRoom;
    private Stack<Room> aBackRooms = new Stack<Room>();

    private ItemList aInventory = new ItemList();
    private int aMaxWeight = 900;

    private Riddle aCurrentRiddle = null;

    private boolean aAllowFight = false;
    private boolean aHasWon = false;

    /**
     * Contructeur de Player
     * @param pR Room de départ
     */
    public Player(final Room pR){
        this.aCurrentRoom = pR;
    }//Player(.)

    /**
     * @return aCurrentRoom
     * getter de aCurrentRoom
     */
    public Room getCurrentRoom(){return this.aCurrentRoom;}//getCurrentRoom()

    /**
     * @param pR
     * Setter de aCurrentRoom
     */
    public void setCurrentRoom(final Room pR) {this.aCurrentRoom = pR;}//setCurrentRoom(.)

    /**
     * @param pNextRoom Room de destination
     * Permet au joueur de se déplacer dans la bonne pièce
     */
    public void walk(final Room pNextRoom){
        this.aBackRooms.push(this.aCurrentRoom);
        this.setCurrentRoom(pNextRoom);
    }//walk(.)

    /**
     * Retour dans la pièce précédente
     */
    public void walkback(){
        this.aCurrentRoom = this.aBackRooms.pop();
    }//walkback()

    /**
     * On vérifie s'il reste des pièces stockées pour revenir en arrière
     * @return boolean false s'il reste des pièces dans la pile ou si la Room au sommet de
     * la pile n'est pas une sortie de la Room actuelle, true sinon
     */
    public boolean cantGoBack(){
        return this.aBackRooms.isEmpty() || !this.aCurrentRoom.isExit(this.aBackRooms.peek());
    }//cantGoBack()

    /**
     * Prendre un Item
     * @param pItemName Nom de l'Item à ramasser
     * @return Message à afficher
     */
    public String takeItem(final String pItemName){
        Item vItem = this.aCurrentRoom.getItem(pItemName);

        if (vItem==null) return ("There is no such item in this room.");

        if (this.aInventory.getTotalWeight() + vItem.getWeight() <= this.aMaxWeight) {
            this.aInventory.addItem(vItem);
            this.aCurrentRoom.removeItem(pItemName);

            return ("You just took " + vItem.getItemString() + " .\n" + this.getInventoryInfo());}

        return ("Maximum weight reached, you can't take this item.");
    }//takeItem(.)

    /**
     * Déposer un Item
     * @param pItemName Nom de l'Item à déposer
     * @return Message à afficher au joueur
     */
    public String dropItem(final String pItemName){
        Item vItem = this.aInventory.getItem(pItemName);

        if (vItem==null) return ("There is no such item in your inventory.");

        this.aCurrentRoom.addItem(vItem);
        this.aInventory.removeItem(pItemName);

        return ("You just dropped " + vItem.getItemString() + " .\n" + this.getInventoryInfo());
    }//dropItem(.)

    /**
     * Infos sur l'inventaire
     * @return String contenant les infos sur l'inventaire
     */
    public String getInventoryInfo(){
        String vItemsString = "";
        if (this.aInventory.isEmpty()) vItemsString = "You don't have any item in your inventory.";
        else vItemsString = "\nItems in your inventory : \n" + this.aInventory.getItemsString();
        return vItemsString + "\nCarried weight : " + this.aInventory.getTotalWeight() + "/" + this.aMaxWeight + "\n";
    }

    /**
     * Permet de manger un cookie et d'en appliquer les effets
     * @return String d'information au joueur
     */
    public String eatCookie(){
        if (this.aInventory.getItem("cookie") != null) {
            this.aMaxWeight += 900;
            this.aInventory.removeItem("cookie");
            return "You just ate a magic cookie. \nYour maximum carriable weight was set to 1900.\n" + this.getInventoryInfo();
        }
        else return "You don't have any cookie in your inventory";
    }

    /**
     * Permet de charger un Beamer
     * @param pS Nom du beamer
     * @return Sortie textuelle pour afficher au joueur
     */
    public String chargeBeamer(final String pS){
        Item vItem = this.aInventory.getItem(pS);
        if (vItem==null) return "There is no such item in your inventory.";
        else if (vItem!=null && vItem.getClass().getSimpleName().equals("Beamer")) {
            Beamer vBeamer = (Beamer) vItem;
            vBeamer.chargeRoom(this.aCurrentRoom);
            return "The current room has been charged in your beamer.";
        }
        else return "You can't do that.";
    }

    /**
     * Permet de fire un Beamer
     * @param pS Nom du beamer
     * @return Sortie textuelle pour afficher au joueur
     */
    public String fireBeamer(final String pS){
        Item vItem = this.aInventory.getItem(pS);
        if (vItem==null) return "There is no such item in your inventory.\n";
        else if (vItem!=null && vItem.getClass().getSimpleName().equals("Beamer")) {
            Beamer vBeamer = (Beamer) vItem;
            if (vBeamer.isCharged()) {
                this.setCurrentRoom(vBeamer.fireRoom());
                this.aBackRooms.clear();
                this.aInventory.removeItem(pS);
                return "You have been teleported to the room charged in the beamer.\n";
            }
            else return "You need to charge the beamer first.\n";
        }
        else return "You can't do that.";
    }

    /**
     * Permet de traiter les cas où le Player est face à une porte fermée
     * @param pDirection Direction souhaitée
     * @return Informations pour le joueur.
     */
    public String lockedDoorProcess(final String pDirection){
        Item vKeyItem = this.aInventory.getItem(this.aCurrentRoom.getKeyString(pDirection));
        if (vKeyItem != null) {
            boolean vIsRiddleDoor = this.aCurrentRoom.getExitDoor(pDirection).getClass().getSimpleName().equals("RiddleDoor");
            if ( (!vIsRiddleDoor) || (vIsRiddleDoor && (((Riddle) vKeyItem).isSolved()))){
                this.aCurrentRoom.getExitDoor(pDirection).unlockDoor();
                this.aInventory.removeItem(this.aCurrentRoom.getKeyString(pDirection));
                return "The door was unlocked using " + vKeyItem.getDescription() +", you can now go through it.";}
            else if (vIsRiddleDoor && !(((Riddle) vKeyItem).isSolved())){
                return "You need to solve the riddle to unlock the door.";
            }
        }
        return "You need '" + this.aCurrentRoom.getKeyString(pDirection) + "' to unlock this door.";
    }

    /**
     * Permet de commencer à résoudre une énigme
     * @param pC Command commençant par solve
     * @return Informations pour le joueur.
     */
    public String solveRiddle(final Command pC){
        Item vRiddleItem = this.aInventory.getItem(pC.getSecondWord());
        if (vRiddleItem != null) {
            if (vRiddleItem.getClass().getSimpleName().equals("Riddle")){
                Riddle vRiddle = (Riddle) vRiddleItem;
                this.aCurrentRiddle = vRiddle;
                return vRiddle.getRiddle();
            }
            return "This is not a riddle";
        }
        return "You don't have this item in your inventory";

    }

    /**
     * Permet au Player de tenter des réponses à l'énigme courante.
     * @param pC Command commençant par guess
     * @return Informations pour le joueur.
     */
    public String guessRiddle(final Command pC){
        if (this.aCurrentRiddle != null) {
            if (this.aCurrentRiddle.guessAnswer(pC.getSecondWord())){
                this.aCurrentRiddle.solved();
                return "Correct answer ! You can unlock the riddle door now !";
            }

            else return "Wrong answer, try again !";
        }
        return "Which riddle do you want to guess ?";
    }

    /**
     * Permet de soigner Walter, d'activer le combat et de renvoyer son discours.
     * @param pWalter Walter à soigner
     * @return Informations pour le joueur.
     */
    public String healWalter(final Walter pWalter){
        if (!this.aCurrentRoom.isWalterHere()) return "There is no one to heal.";
        if (this.aInventory.getItem("medkit")!=null){
            this.aAllowFight = true;
            return pWalter.heal();
        }
        return "You need a medkit.";
    }

    /**
     * Permet de combattre le monstre
     * @return Informations pour le joueur.
     */
    public String fightMonster(){
        if (this.aAllowFight) {
            if (this.aInventory.getItem("sword") == null || this.aInventory.getItem("shield") == null)
                return "You need a shield and a sword to fight the monster.";
            else {
                this.aHasWon = true;
                return "You have defeated the monster.";
            }
        }
        return "You can't fight the monster before healing Walter.";
    }

    /**
     * Getter de aHasWon
     * @return true si le joueur a gagné, false sinon.
     */
    public boolean hasWon() {
        return this.aHasWon;
    }

}
