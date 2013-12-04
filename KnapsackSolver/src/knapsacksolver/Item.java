/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package knapsacksolver;
/**
 *
 * @author Mafiacomp
 */
public class Item{

    private int weight;
    private int price;
    private boolean isInPack;
    
    public Item(int weight, int price) {
        this.weight = weight;
        this.price = price;
        this.isInPack = false;
    }

    public boolean isIsInPack() {
        return isInPack;
    }

    public void setIsInPack(boolean isInPack) {
        this.isInPack = isInPack;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPrice(int price) {
        this.price = price;
    }
   
}
