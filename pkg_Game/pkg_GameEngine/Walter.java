package pkg_Game.pkg_GameEngine;

/**
 * PNJ à soigner qui délivrera des informations nécessaires à la suite du jeu
 *
 * @author Raphaël CANIN
 * @version 2021
 */
public class Walter
{
    private String aSpeech;
    private boolean aNeedsCare = true;

    /**
     * Constructeur par défaut
     */
    public Walter(){
        this.aSpeech = "\nWALTER : Thank you for looking after me, valiant explorer. \nI was injured while trying to save my companions from \na terrible monster that is still lurking in the building.\n\nKill the monster to bring justice to my brothers!\nYou need a sword and a shield to beat it.\nWhen you are ready, use 'fight'.\n";
    }
    
    /**
     * Permet de soigner Walter
     * @return Son discours
     */
    public String heal() {
    this.aNeedsCare = false;
    return this.aSpeech; 
    }

}
