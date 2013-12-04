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
public class KnapsackSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Solver solver = new Solver("C:\\Users\\Daniel\\Documents\\NetBeansProjects\\KnapsackSolver\\20things_knapsack.dat");
        solver.computeKnapsack("C:\\Users\\Daniel\\Documents\\NetBeansProjects\\KnapsackSolver\\20things_knapsack_out.dat");
    }
    
    
    
}
