/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.execution;

import java.util.HashMap;
import java.util.Stack;
import tinyjvm.structure.Frame;
import tinyjvm.structure.ObjectHeap;
import tinyjvm.structure.variable.MyException;
import tinyjvm.structure.variable.MyObject;
import tinyjvm.structure.variable.Variable;

/**
 *
 * @author Daniel
 */
public class GarbageCollector {
    
    
    public static void cleanup(Stack<Frame> frameStack) {
        //mark
        for (Frame frame : frameStack) {
            System.out.println("Garbage frame: " + frame.methodName);
            for (int i = 0; i < frame.localVariable.length; i++) {
                if (frame.localVariable[i] instanceof MyObject) {
                    markObjects((MyObject) frame.localVariable[i]);
                }
            }
            for (Variable var : frame.frameStack) {
                if (var instanceof MyObject) {
                    markObjects((MyObject) var);
                }
            }
        }

        //sweep
        HashMap<String, MyObject> newObjectHeap = new HashMap();
        ObjectHeap oh = ObjectHeap.getInstance();
        for (String key : oh.objectHeap.keySet()) {
            MyObject obj = oh.objectHeap.get(key);
            if (obj.mark) {
                obj.mark = false;
                newObjectHeap.put(key, obj);
            }
        }
        
        oh.objectHeap = newObjectHeap;

    }

    //Recursively mark objects contains in instance variables
    private static void markObjects(MyObject markObject) {
        if(markObject instanceof MyException) return;      
        if(markObject.mark) return;
        markObject.mark = true;
        for(Variable tmpVar : markObject.instanceVar.values()){
            if(tmpVar instanceof MyObject){
                markObjects((MyObject) tmpVar);
            }
        }
    }
}
