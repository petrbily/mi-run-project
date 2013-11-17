/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure.variable;

import tinyjvm.structure.classfile.FieldInfo;

/**
 *
 * @author Daniel
 */
public class MyInteger extends Variable{
    private int value;

    public MyInteger(FieldInfo info){
        super(info);
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String getType(){
        return "Ïnteger";
    }
    
    
    
}
