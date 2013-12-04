/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package knapsacksolver;

/**
 *
 * @author Daniel
 */
public class Solver {
    
    private int n; //pocet veci
    private int M; //kapacita batohu
    private Item items[]; //pole prvku
    private int bestPrice = 0;
    private int [] solution;
    //private static int totalCount = 0;
    
    public Solver(String inputFile){        
        
        //String knapsack_definition = "20 540 61 10 39 219 34 245 91 122 60 85 24 48 57 201 63 100 54 246 4 41 69 90 45 234 46 165 87 172 74 111 84 35 49 102 68 145 42 89 29 215";//readFile(inputFile);
        String knapsack_definition = readFile(inputFile);
        String [] splitting = knapsack_definition.split(" ");
        this.getValues(splitting);
        /*
        int [] weights = {6, 2, 8, 1, 44, 7, 28, 5, 16, 9, 3, 12, 35, 18, 45};
        int [] prices = {247 , 210 , 163 , 93 , 255 , 244 , 190 , 117 , 234 , 105 , 29 , 135 , 96 , 203 , 238};
        
        for (int i = 0; i < n; i++) {
            items[i] = new Item(weights[i], prices[i]);
        }
        */
        
    }
    public native void print(String s);
    public native String readFile(String fileName);
    public native void writeToFile(String fileName, String text);
    
    public int computeKnapsack(String outputFile){
        bestPrice = 0;
        knap(items, 0,0,0);
        print("Best price: " + bestPrice);
        //System.out.println("nejlepsi cen: " + bestPrice);
        String result = "";
        for(int i = 0; i < solution.length ; i++){
            //System.out.print(solution[i] + ",");
            result += solution[i];
        }
        print("Result knapsack: " + result);
        writeToFile(outputFile ,"Best price: " + bestPrice + " Knapsack: " + result); 
        return bestPrice;
    }
    
    
    //reseni pomoci prohledavanim do hloubky
    private void knap(Item[] knapstack, int index, int weight, int price) {
        //totalCount++;

        if (price > bestPrice) {
            bestPrice = price;
            for(int i = 0; i < knapstack.length; i++){
                solution[i] = (knapstack[i].isIsInPack())? 0 : 1;
            }
        }

        for (int i = index; i < knapstack.length; i++) {
            knapstack[i].setIsInPack(true);
            int actWeight = knapstack[i].getWeight() + weight;
            if (actWeight > M) {
                continue;
            }
            knap(knapstack, i + 1, actWeight, price + knapstack[i].getPrice());
            knapstack[i].setIsInPack(false);
        }
    }
    
    private void getValues(String[] split) {
        n = Integer.valueOf(split[0]);
        M = Integer.valueOf(split[1]);
        int index = 0;
        items = new Item[n];
        solution = new int[n];
        
        for (int i = 2; i < split.length; i += 2) {
            if (i != 2) {
                index = (i - 2) / 2;
            }
            items[index] = new Item(Integer.valueOf(split[i]), Integer.valueOf(split[i + 1]));
            //print("Items [" + index + "] =" + split[i] + "x" + split[i+1] );
        }
    }
}
