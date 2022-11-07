package pkg_Game.pkg_GameEngine.pkg_Item;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;

/**
 * Class used to implement items in the game
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public class Item
{
    private String aDescription;
    private int aWeight;
    private String aItemString;
    private String aLongDescription;


    /**
     * Constructeur naturel
     * @param pDescription Description de l'item
     * @param pWeight Poids de l'item
     * @param pItemString Nom de l'item avec article
     * @param pLDescription Description longue de l'item
     * @param pIsBeamer true si l'Item est un Beamer, false sinon
     */
    public Item(final String pDescription, final int pWeight, final String pItemString, final String pLDescription){
        this.aWeight = pWeight;
        this.aDescription = pDescription;
        this.aItemString = pItemString;
        this.aLongDescription = pLDescription;
    }//Item(.,.,.,.,.)

    public String getDescription(){return this.aDescription;}
    public int getWeight(){return this.aWeight;}
    public String getItemString(){return this.aItemString;}
    public String getLongDescription(){return this.aLongDescription;}
}
