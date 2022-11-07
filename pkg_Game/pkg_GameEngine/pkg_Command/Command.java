package pkg_Game.pkg_GameEngine.pkg_Command;


import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Item.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;
 /**
 * Classe Command servant à formater les commandes textes entrées par l'utilisateur.
 * @author Raphaël CANIN
 * @version 2020.09.25
 */
public class Command
{
   // ### Attributs ###

   private String aCommandWord;
   private String aSecondWord;

   // ### Constructeur ###

   /**
    * Contructeur naturel
    * @param p1 String pour initialiser aCommandWord
    * @param p2 String pour initialiser aSecondWord
    */
   public Command(final String p1, final String p2)
   {
       this.aCommandWord = p1;
       this.aSecondWord = p2;
   }//Command(.)


   // ### Accesseurs ###

   /**
    * Accesseur de aCommandWord
    * @return aCommandWord
    */
   public String getCommandWord(){return this.aCommandWord;}//getCommandWord()

   /**
    * Accesseur de aSecondWord
    * @return aSecondWord
    */
   public String getSecondWord() {return this.aSecondWord;}//getSecondWord()

   // ### Autres Méthodes ###

   /**
    * Vérifie si l'objet contient un deuxième mot
    * @return boolean Vrai s'il y a un deuxième mot, faux sinon.
    */
   public boolean hasSecondWord()
   {
       return this.aSecondWord != null;
   }

   /**
    * Vérifie si l'objet contient un mot de commande vide.
    * @return boolean Vrai si la commande est invalide, faux sinon.
    */
   public boolean isUnknown()
   {
       return this.aCommandWord == null;
   }
} // Command
