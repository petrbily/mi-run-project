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
public class MyString extends Variable{
    
    private String value;
    
    public MyString(){
        value = "";
    }
    
    public MyString(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public String getType(){
        return "String";
    }
    
    @Override
    public String toString(){
        return this.value;
    }
}
