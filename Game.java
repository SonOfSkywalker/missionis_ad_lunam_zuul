import pkg_Game.pkg_UserInterface.*;
import pkg_Game.pkg_GameEngine.*;

/**
 * Classe Game servant de boucle principale au programme.
 * @author Raphaël CANIN
 * @version 2020
 */
public class Game
{
    private UserInterface aGui;
    private GameEngine aEngine;

    /**
     * Crée une partie
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
    
    /**
     * Getter de aEngine
     * @return aEngine
     */
    public GameEngine getEngine(){
        return this.aEngine;
    }
    
} // Game
