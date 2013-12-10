/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure.variable;

import tinyjvm.MyLogger;

/**
 *
 * @author Daniel
 */
public class MyArray extends Variable{
    
    private String type;
    private Variable[] array;
    
    public MyArray(int count, String type){
        this.type = type;
        this.array = new Variable[count];
    }
    
    @Override
    public String getType(){
        return "Array";
    }

    public void addValue(int index, Variable value) {
        array[index] = value;
    }
    
    public Variable getValue(int index){
        return array[index];
    }
    
    public MyInteger getMyIntegerValue(int index){
        try{
            return (MyInteger)array[index];
        }catch(ClassCastException ex){
            MyLogger.logError("ClassCastException: Try to get MyInteger from array of " + type + " type.");
        }
        return null;
    }

    public int getLength() {
        return array.length;
    }
    
    @Override
    public String toString(){
        return array.toString();
    }
}
