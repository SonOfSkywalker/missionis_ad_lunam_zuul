package pkg_Game.pkg_GameEngine.pkg_Item;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import pkg_Game.pkg_GameEngine.*;
import pkg_Game.pkg_GameEngine.pkg_Command.*;
import pkg_Game.pkg_GameEngine.pkg_Room.*;
/**
 * Classe gérant la liste d'items
 *
 * @author Raphaël CANIN
 * @version 2020
 */
public class ItemList
{
    private HashMap<String,Item> aItemList = new HashMap<String,Item>();

    /**
     * Constructeur de la class
     */
    public ItemList(){}//ItemList()

    /**
     * Permet d'ajouter un item
     * @param pItem item à ajouter
     */
    public void addItem(final Item pItem){
        this.aItemList.put(pItem.getDescription(),pItem);
    }//setItem(.)

    /**
     * Permet de retirer un item
     * @param pItemString Nom de l'item à retirer
     */
    public void removeItem(final String pItemString){
        this.aItemList.remove(pItemString);
    }//removeItem(.)

    /**
     * Permet de récupérer un item en entrant son nom
     * @param pS String contenant le nom d'un item
     * @return Renvoie l'item de la HashMap correspondant à la clé passée en paramètre, null sinon
     */
    public Item getItem(final String pS){
        return this.aItemList.get(pS);
    }//getItem(.)

    /**
     * Permet de récupérer la liste des Items sous forme de String
     * @return Liste des Items sous forme de String
     */
    public String getItemsString(){
        String vItemsS = "";
        StringBuilder vItemsString = new StringBuilder("");
        this.aItemList.forEach((vDescription, vItem)->vItemsString.append(vItem.getItemString() +" (" + vItem.getWeight() + ") \n"));
        vItemsS = vItemsString.toString();
        return vItemsS;
    }

    /**
     * Permet de savoir si la HashMap est vide ou non
     * @return true si la HashMap est vide, false sinon
     */
    public boolean isEmpty(){return this.aItemList.isEmpty();}//isEmpty()

    /**
     * Permet de récupérer le poids total des Items dans une ItemList
     * @return Poids du total des Items
     */
    public int getTotalWeight(){
        int vW = 0;
        Set<String> vKeys = this.aItemList.keySet();

        for(String vKey : vKeys) {
            vW += this.aItemList.get(vKey).getWeight();
        }
        return vW;
    }

}
