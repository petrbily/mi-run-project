/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure.variable;

/**
 *
 * @author Daniel
 */
public class MyInteger extends Variable{
    private int value;

    public MyInteger(){
        value = 0;
    }
    
    public MyInteger(int value){
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String getType(){
        return "Integer";
    }
    
    public MyInteger add(MyInteger a){
        return new MyInteger(this.value + a.getValue());
    }
    
    public void increment(int a){
        this.value += a;
    }
    
    @Override
    public String toString(){
        return this.value + "";
    }
    
}
