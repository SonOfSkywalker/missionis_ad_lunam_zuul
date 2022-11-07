
package pkg_Game.pkg_UserInterface;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Classe permettant la lecture de fichiers texte.
 * Une partie de lecture() provient de M. Denis BUREAU.
 * @author Raphaël CANIN
 * @version 2020
 */
public class LectureFichier
{
    /**
     * Lecture d'un fichier et renvoie de l'ensemble des lignes sous forme de String séparées
     * par une virgule (pour utiliser split(",") ensuite)
     * @param pNomFichier Nom du fichier à lire
     * @return String du fichier lu.
     */
    public static String lecture( final String pNomFichier )
    {
        Scanner vSc;
        StringBuilder vB = new StringBuilder("");
        try {
            vSc = new Scanner( new File( pNomFichier ) );
            while ( vSc.hasNextLine() ) {
                vB.append(vSc.nextLine() + ",");
            }
            return vB.toString();
        } 
        catch ( final FileNotFoundException pFNFE ) {
            return null;
        } 
    } // lecture
} // LectureFichier