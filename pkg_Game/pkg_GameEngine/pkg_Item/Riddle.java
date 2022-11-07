package pkg_Game.pkg_GameEngine.pkg_Item;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;

/**
 * Item comportant une énigme
 *
 * @author Raphaël CANIN
 * @version 2021
 */
public class Riddle extends Item
{
    private boolean aIsSolved = false;
    private String aRiddle;
    private String aAnswer;

    /**
     * Constructeur
     */
    public Riddle(){
        super("riddle", 100, "a riddle", "This device unlock the riddle door after solving a riddle.\nUse 'solve' to print the riddle.");
    }//Riddle()

    /**
     * Getter de aIsSolved
     * @return l'état de résolution de l'énigme
     */
    public boolean isSolved(){
        return this.aIsSolved;
    }

    /**
     * À appeler lorsque l'énigme est résolue
     */
    public void solved(){
        this.aIsSolved = true;
    }

    /**
     * Setter de aRiddle
     * @param pRiddle Énoncé de l'énigme
     */
    public void setRiddle(final String pRiddle){
        this.aRiddle  = pRiddle;
    }

    /**
     * Set de aAnswer
     * @param Réponse à l'énigme
     */
    public void setAnswer(final String pAnswer){
        this.aAnswer  = pAnswer;
    }

    /**
     * Getter de aRiddle
     * @return Énoncé de l'énigme
     */
    public String getRiddle(){
        return this.aRiddle;
    }
    /**
     * @param pGuess Tentative de réponse
     * @return true si la réponse est vraie, faux sinon.
     */
    public boolean guessAnswer(final String pGuess){
        return pGuess.equals(this.aAnswer);
    }
}
