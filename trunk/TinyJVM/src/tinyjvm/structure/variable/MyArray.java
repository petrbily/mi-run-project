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
    
    public MyArray(MyInteger count, String type){
        this.type = type;
        this.array = new Variable[count.getValue()];
    }
    
    @Override
    public String getType(){
        return "Array";
    }

    public void addValue(MyInteger index, Variable value) {
        array[index.getValue()] = value;
    }
    
    public Variable getValue(MyInteger index){
        return array[index.getValue()];
    }
    
    public MyInteger getMyIntegerValue(MyInteger index){
        try{
            return (MyInteger)array[index.getValue()];
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
