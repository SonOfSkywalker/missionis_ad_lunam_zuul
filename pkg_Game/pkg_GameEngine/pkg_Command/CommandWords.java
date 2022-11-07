
package pkg_Game.pkg_GameEngine.pkg_Command;
 /**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau + Raphael CANIN
 * @version 2008.03.30 + 2019.09.25 + 2020.09.22
 */
public class CommandWords
{
    // a constant array that will hold all valid command words
    private final String[] aValidCommands = {"go","help","quit","look","eat","back","test","take","drop","inventory", "charge", "fire", "alea", "guess","solve","heal", "fight"};

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        //
    } // CommandWords()

    /**
     * Check whether a given String is a valid command word. 
     * @param pString Commande à valider ou non
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    {
        for ( int i=0; i<this.aValidCommands.length; i++ ) {
            if ( this.aValidCommands[i].equals( pString ) )
                return true;
        } // for
        // if we get here, the string was not found in the commands
        return false;
    } // isCommand()
    
    /**
     * Renvoie les différentes commandes disponibles sous forme de String
     * @return String contenant toutes les commandes disponibles
     */
    public String getCommandList(){
        StringBuilder vS = new StringBuilder("");
        for (String vCommand : this.aValidCommands) {
            vS.append(vCommand + " ");
        }
        return vS.toString();
    }//getCommandList()
   
    
} // CommandWords