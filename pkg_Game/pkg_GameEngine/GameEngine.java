package pkg_Game.pkg_GameEngine;


import pkg_Game.pkg_UserInterface.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;

/**
 *  This class is part of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.
 *
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that
 *  the parser returns.
 *
 * @author  Michael Kolling and David J. Barnes, Raphaël CANIN
 * @version 1.0 (Jan 2003) DB edited (2019) RC edited (2020)
 */
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Stack;
import java.util.ArrayList;

public class GameEngine
{
    // ### Attributs ###
    private Parser aParser = new Parser();
    private UserInterface aGui;
    private Player aPlayer;
    private int aNbMoves;
    private boolean aTestMode = true;
    private Walter aWalter = new Walter();

    public static final int MAXMOVES = 100;
    //Création d'une HashMap contenant toutes les Room créées
    private HashMap<String,Room> aAllRooms = new HashMap<String,Room>();
    //private ArrayList<Room> aAllRooms = new ArrayList<Room>();

    public HashMap<String,Room> getAllRooms(){return aAllRooms;}

    /**
     * Constructeur d'objets de la classe GameEngine
     */
    public GameEngine(){
        this.createRooms();
    }

    /**
     * Setter de la User Interface
     * @param pUI UserInterface à utiliser à utiliser
     */
    public void setGUI( final UserInterface pUI )
    {
        this.aGui = pUI;
        this.printWelcome();
    }

    /**
     * Création des Rooms du jeu + Affectation de la Room de départ
     */
    private void createRooms()
    {
        //Création des Rooms
        Room vControlPanel = new Room("in front of the rocket's control panel","ControlPanel.jpg");    aAllRooms.put("Control Panel",vControlPanel);
        Room vStorage = new Room("in the rocket's storage unit","Storage.jpg");   aAllRooms.put("Storage",vStorage);
        Room vHall =     new Room("in the space rocket's hall", "Hall.jpg");    aAllRooms.put("Hall",vHall);
        Room vRocket =     new Room("at the foot of the rocket", "Rocket.jpg");   aAllRooms.put("Rocket",vRocket);

        Room vEntrance =  new Room("in the lunar base's entrance", "Entrance.jpg");   aAllRooms.put("Entrance",vEntrance);

        Room vLivingArea =  new Room("in the base's living area","LivingArea.jpg");   aAllRooms.put("Living Area",vLivingArea);
        Room vBathroom =  new Room("in the base's bathroom", "Bathroom.jpg");     aAllRooms.put("Bathroom",vBathroom);
        Room vDormitory =  new Room("in the base's dormitory", "Dormitory.jpg");   aAllRooms.put("Dormitory",vDormitory);

        Room vAirDuct =  new Room("in the base's air duct", "AirDuct.jpg");     aAllRooms.put("Air Duct",vAirDuct);
        Room vLocker =  new Room("in the base's locker room", "Locker.jpg");     aAllRooms.put("Locker",vLocker);
        Room vOxygen =  new Room("in the base's oxygen reactor unit", "Oxygen.jpg");   aAllRooms.put("Oxygen", vOxygen);
        Room vEngine =  new Room("in the base's engine room", "Engine.jpg");      aAllRooms.put("Engine",vEngine);

        Room vComm =  new Room("in the base's communications room", "Communications.jpg");      aAllRooms.put("Communications",vComm);
        Room vMonitoring =  new Room("in the base's control unit", "Monitoring.jpg");      aAllRooms.put("Monitoring",vMonitoring);

        ArrayList<Room> vRoomsList = new ArrayList<Room>(this.aAllRooms.values());

        Room vTransporterRoom = new TransporterRoom("in the transportation room", "TransporterRoom.jpg", vRoomsList);
        aAllRooms.put("TransporterRoom",vTransporterRoom);

        // Positionnement des sorties
        //vEntrance.setExit("north", vRocket);
        vEntrance.setExit("south", vLocker);
        vEntrance.setExit("east", vLivingArea);

        vHall.setExit("west", vControlPanel);
        vHall.setExit("down", vRocket);

        vControlPanel.setExit("down",vStorage);
        vControlPanel.setExit("east",vHall);

        vStorage.setExit("up", vControlPanel);

        vRocket.setExit("up", vHall);
        vRocket.setExit("south", vEntrance);

        vLivingArea.setExit("west", vEntrance);
        vLivingArea.setExit("south", vDormitory);
        vLivingArea.setExit("east", vBathroom);

        vBathroom.setExit("west", vLivingArea);

        vDormitory.setExit("north", vLivingArea);
        vDormitory.setExit("south", vAirDuct);

        vAirDuct.setExit("north", vDormitory);

        vLocker.setExit("north", vEntrance);
        vLocker.setExit("west", vComm);
        vLocker.setExit("south", vOxygen);

        vOxygen.setExit("north", vLocker);

        vEngine.setExit("south", vComm);

        vComm.setExit("north", vEngine);
        vComm.setExit("east", vLocker);
        vComm.setExit("south", vMonitoring);

        vMonitoring.setExit("north", vComm);

        vMonitoring.setExit("south", vTransporterRoom);

        // Items

        Item vScrewdriver = new Item("screwdriver", 200, "a screwdriver","This screwdriver can be used to unscrew cruciform screws.");
        Item vPass = new Item("pass", 100, "a pass","This pass is used to unlock doors with a badge reader.");

        Item vSword = new Item("sword", 400, "a sword","A sword can be used to inflict more damage to an opponent.");
        Item vShield = new Item("shield", 900, "a shield","A shield reduces the damage received by 50%.");
        Item vCookie = new Item("cookie", 0, "a cookie","A magic cookie can increase your maximum carriable weight.");
        Item vBeamer = new Beamer();
        Riddle vRiddle = new Riddle();

        vRiddle.setRiddle("\nA father and a son are both 36 years old. \nKnowing that the father is 30 years older than the son, how old is the son?\nUse 'guess' + your answer to solve the riddle.\n");
        vRiddle.setAnswer("3");

        Item vRiddleItem = (Item) vRiddle;

        Item vMedKit = new Item("medkit", 200, "a medkit","The care kit can be used to heal a character.");
        vAirDuct.addItem(vRiddleItem);

        vStorage.addItem(vScrewdriver);
        vLivingArea.addItem(vShield);
        vBathroom.addItem(vMedKit);
        vEngine.addItem(vPass);
        vOxygen.addItem(vSword);
        vDormitory.addItem(vCookie);
        vComm.addItem(vBeamer);

        // Doors

        Door vAirDuctDoor = new Door(true,"screwdriver");
        Door vMonitoringAndOxygenDoor = new Door(true, "pass");
        Door vEngineDoor = new RiddleDoor(true, "riddle");

        vEngine.setDoor("south", vEngineDoor);
        vComm.setDoor("north", vEngineDoor);

        vComm.setDoor("south", vMonitoringAndOxygenDoor);
        vMonitoring.setDoor("north", vMonitoringAndOxygenDoor);
        vLocker.setDoor("south", vMonitoringAndOxygenDoor);
        vOxygen.setDoor("north", vMonitoringAndOxygenDoor);

        vDormitory.setDoor("south", vAirDuctDoor);
        vAirDuct.setDoor("north", vAirDuctDoor);

        //NPC

        vMonitoring.walterIsHere();

        // Player
        aPlayer = new Player(vStorage);

    }//createRooms()
    /**
     * printWelcome affiche un message de bienvenue.
     */
    private void printWelcome()
    {
        this.aGui.println("Welcome to the world of Zuul !");
        this.aGui.println("World of Zuul is a new, incredibly boring adventure game.\n");
        this.aGui.println("Type 'help' if you need help.");
        this.printLocationInfo();
    }//printWelcome()

    /**
     * Affichage de la description de la pièce + les différentes sorties disponibles pour la pièce actuelle + l'image
     */
    private void printLocationInfo(){
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        this.aGui.println("You have " + (MAXMOVES - this.aNbMoves) + " move(s) left.\n");
    }//printLocationInfo()

    /**
     * Appelle la bonne méthode en fonction de la commande entrée en paramètre
     * @param pC Command commande entrée par l'utilisateur
     */
    public void interpretCommand(final String pC)
    {
        this.aGui.println( "> " + pC );
        Command vCommand = this.aParser.getCommand( pC );

        if (this.aNbMoves >= MAXMOVES-1) lose();

        this.aNbMoves +=1;

        if (vCommand.isUnknown())
        {
            this.aGui.println( "I don't know what you mean..." );
            this.aNbMoves += -1;
            return;
        }

        switch (vCommand.getCommandWord()) {
            case "quit":        this.quit(vCommand);    break;
            case "go":          this.goRoom(vCommand);  break;
            case "help":        this.printHelp();       break;
            case "look":        this.look(vCommand);    break;
            case "eat":         this.eat(vCommand);     break;
            case "back":        this.back(vCommand);    break;
            case "test":        this.test(vCommand);    break;
            case "take":        this.take(vCommand);    break;
            case "drop":        this.drop(vCommand);    break;
            case "inventory":   this.inventory();       break;
            case "charge":      this.charge(vCommand);  break;
            case "fire":        this.fire(vCommand);    break;
            case "alea":        this.alea(vCommand);    break;
            case "solve":       this.solve(vCommand);   break;
            case "guess":       this.guess(vCommand);   break;
            case "heal":        this.heal();            break;
            case "fight":       this.fight();           break;

            default: this.aGui.println("An error has occured.");
        }

    }//interpretCommand(.)

    // implementations of user commands:

    /**
     * Affiche de l'aide
     */
    private void printHelp()
    {
        this.aGui.println("You just have landed on the Moon searching for survivors of a terrible crash.\n");
        this.aGui.println("Your command words are :");
        this.aGui.println(this.aParser.getCommands());
    }//printHelp()

    /**
     * Exécution de "go"
     * @param pDirection Command pour la direction souhaitée
     */
    private void goRoom(final Command pDirection)
    {
        if (!(pDirection.hasSecondWord()))
        {
            this.aGui.println( "Go where?" );
            return;
        }

        Room vNextRoom = null;

        String vDirectionWord = pDirection.getSecondWord();

        vNextRoom = this.aPlayer.getCurrentRoom().getExit(vDirectionWord);

        //Cas spéciaux
        if (vNextRoom == null) this.aGui.println("There is no door!");

        else if (vNextRoom == Room.UNKNOWN_DIRECTION) this.aGui.println("Unknown direction!");
        // Fin des cas spéciaux

        else
        {
            if (this.aPlayer.getCurrentRoom().canGoThrough(vDirectionWord)) {
                this.aPlayer.walk(vNextRoom);
            }
            else {
                this.aGui.println(this.aPlayer.lockedDoorProcess(vDirectionWord));
            }
            this.printLocationInfo();
        }
    }//goRoom(.)

    /**
     * Vérifier ce qu'il faut quitter
     * @param p1 Command commande de quit
     */
    private void quit(final Command p1)
    {
        if (p1.hasSecondWord())
        {
            this.aGui.println("Quit what ?");
        }
        else
        {
            this.endGame();
        }
    }//quit(.)

    /**
     * Affiche la description longue
     * @param pC Command commande entrée par l'utilisateur commençant par look
     */
    private void look(final Command pC){
        if (pC.getSecondWord()==null){
            this.printLocationInfo();
        }
        else {
            if (this.aPlayer.getCurrentRoom().getItem(pC.getSecondWord()) != null) {
                this.aGui.println(this.aPlayer.getCurrentRoom().getItem(pC.getSecondWord()).getLongDescription());
            }
            else this.aGui.println("There is no such item in the room.");

        }
    }//look(.)

    /**
     * Exécution de la commande eat
     * @param pC Command commençant par eat
     */
    private void eat(final Command pC){
        if (!pC.hasSecondWord()) this.aGui.println("Eat what ?");
        else if (pC.getSecondWord().equals("cookie")) {
            this.aGui.println(this.aPlayer.eatCookie());
        }

        else this.aGui.println("This item can't be eaten.");

    }//eat(.)

    /**
     * Exécution de la commande back
     * @param pCommand Command commençant par back
     */
    private void back(final Command pCommand){
        if (pCommand.hasSecondWord()) this.aGui.println("The back command doesn't accept a second word.");
        else if (this.aPlayer.cantGoBack()) this.aGui.println("You can't go back.");
        else
        {
            this.aPlayer.walkback();
            this.printLocationInfo();
        }
    }

    /**
     * Procédure de fin de partie
     */
    private void endGame()
    {
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    }//endGame()

    /**
     * Procédure de test
     * @param pC commande
     */
    private void test(final Command pC){
        if(!pC.hasSecondWord())
            this.aGui.println("Which file do you want to use for the test ?");

        else{
            LectureFichier vLecture = new LectureFichier();
            String vCommandes = vLecture.lecture(pC.getSecondWord()+".txt");
            if (vCommandes==null) this.aGui.println("This file doesn't exist, try again.");
            else {
                String[] vTab = vCommandes.split(",");
                for (String vCommand : vTab) {
                    this.interpretCommand(vCommand);
                }
            }

        }
    }

    /**
     * Procédure exécutant la commande take et affichant un message d'information au joueur
     * @param pC Command commençant par take
     */
    private void take(final Command pC){
        if (!pC.hasSecondWord()) this.aGui.println("Which item do you want to take ?");

        else{
            this.aGui.println(aPlayer.takeItem(pC.getSecondWord()));
        }
        this.printLocationInfo();
    }//take(.)

    /**
     * Procédure exécutant la commande drop et affichant un message d'information au joueur
     * @param pC Command commençant par drop
     */
    private void drop(final Command pC){
        if (!pC.hasSecondWord()) this.aGui.println("Which item do you want to drop ?");

        else{
            this.aGui.println(this.aPlayer.dropItem(pC.getSecondWord()));
        }
        this.printLocationInfo();
    }//drop(.)

    /**
     * Procédure exécutant la commande inventory
     */
    private void inventory(){
        this.aGui.println(this.aPlayer.getInventoryInfo());
    }//inventory(.)

    /**
     * Procédure exécutant la commande charge
     * @param pC Command commençant par charge
     */
    private void charge(final Command pC){
        if (pC.hasSecondWord()){
            this.aGui.println(this.aPlayer.chargeBeamer(pC.getSecondWord()));
        }
        else this.aGui.println("Charge what ?");
    }//charge(.)

    /**
     * Procédure exécutant la commande fire
     * @param pC Command commençant par fire
     */
    private void fire(final Command pC){
        if (pC.hasSecondWord()){
            this.aGui.println(this.aPlayer.fireBeamer(pC.getSecondWord()));
        }
        else this.aGui.println("Fire what ?");
        this.printLocationInfo();
    }//charge(.)

    /**
     * Procédure exécutant la commande alea
     * @param pC Command commençant par alea
     */
    private void alea(final Command pC){
        if (this.aTestMode) {
            TransporterRoom vTR = (TransporterRoom) this.aAllRooms.get("TransporterRoom");
            if (pC.hasSecondWord() && (this.aAllRooms.get(pC.getSecondWord()) != null) ) {
                vTR.setRoomTestMode(this.aAllRooms.get(pC.getSecondWord()));
                this.aGui.println("The alea room has been set to " + pC.getSecondWord() + ".\n");
            }
            else {
                vTR.setRoomTestMode(null);
                this.aGui.println("The alea room has been deleted.");
            }
        }
        else this.aGui.println("You need to be in test mode to use this command");
    }//alea(.)

    /**
     * Procédure exécutant la commande solve
     * @param pC Command commençant par solve
     */
    private void solve(final Command pC){
        if (pC.hasSecondWord()){
            this.aGui.println(this.aPlayer.solveRiddle(pC));
        }
        else this.aGui.println("Solve what ?");
    }

    /**
     * Procédure exécutant la commande guess
     * @param pC Command commençant par guess
     */
    private void guess(final Command pC){
        if (pC.hasSecondWord()){
            this.aGui.println(this.aPlayer.guessRiddle(pC));
        }
        else this.aGui.println("Guess what ?");
    }

    /**
     * Commande de Game Over. Quitte la partie.
     */
    private void lose() {
        this.aGui.println("You exceeded the maximum number of moves. Game Over");
        this.aGui.enable( false );
    }
    
    /**
     * Procédure exécutant la commande heal
     */
    private void heal(){
        this.aGui.println(this.aPlayer.healWalter(this.aWalter));
    }

    /**
     * Procédure exécutant la commande fight
     */
    private void fight(){
        this.aGui.println(this.aPlayer.fightMonster());
        if (this.aPlayer.hasWon()) {
            this.victory();
        }
    }

    /**
     * Procédure exécutant la victoire du joueur.
     */
    private void victory() {
        this.aGui.println("    .......   ....... \n   ...       ...     \n   ... ..... ... .....\n   ...   ... ...   ...\n    .. .. .   .. .. . ");
        this.aGui.println("\nWell done ! You just won the game !");
        this.aGui.enable( false );
    }

}
