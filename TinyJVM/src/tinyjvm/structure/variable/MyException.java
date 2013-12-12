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
public class MyException extends MyObject{
    
    /*
    public MyException(String id) {
        super(id);
    }
    */
    
    public MyException(MyObject obj){
        super(obj.id, obj.className, obj.instanceVar);
        
    }
    
}
