package tinyjvm.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import tinyjvm.structure.classfile.MethodInfo;
import tinyjvm.structure.variable.Variable;

/**
 *
 * @author Daniel
 */
public class Frame {
    
    public Stack<Variable> frameStack = new Stack();
    public Stack<Integer> localStack = new Stack();
    
    //TODO - Change to Variable
    public Integer [] localVariable;
    public MethodInfo methodInfo;
    
    public Frame(MethodInfo methodInfo){
        this.methodInfo = methodInfo;
        this.localVariable = new Integer[methodInfo.getMaxLocals()];
    }
    
    public byte [] getMethodCode(){
        return this.methodInfo.getByteCode();
    }
}
