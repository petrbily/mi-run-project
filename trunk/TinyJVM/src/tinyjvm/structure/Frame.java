package tinyjvm.structure;

import java.util.Stack;
import tinyjvm.structure.classfile.MethodInfo;
import tinyjvm.structure.variable.Variable;

/**
 *
 * @author Daniel
 */
public class Frame {
    
    public Stack<Variable> frameStack = new Stack();
    public MethodInfo methodInfo;
    
    public Frame(MethodInfo methodInfo){
        this.methodInfo = methodInfo;
    }
}
