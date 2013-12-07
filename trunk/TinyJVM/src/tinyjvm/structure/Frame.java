package tinyjvm.structure;

import java.util.Stack;
import tinyjvm.MyLogger;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;
import tinyjvm.structure.classfile.MethodInfo;
import tinyjvm.structure.variable.MyArray;
import tinyjvm.structure.variable.MyInteger;
import tinyjvm.structure.variable.MyObject;
import tinyjvm.structure.variable.Variable;

/**
 *
 * @author Daniel
 */
public class Frame {
    
    public Stack<Variable> frameStack = new Stack();
    
    public Variable [] localVariable;
    public MethodInfo methodInfo;
    public String methodName;
    private String descriptor;
    public ClassFile classFile;
    
    public Frame(ClassFile classFile, MethodInfo methodInfo){
        this.classFile = classFile;
        //TODO exception kdyz nebude metoda existovat
        this.methodInfo = methodInfo;//classFile.getMethod(methodName);
        this.methodName = methodInfo.getName();//methodName;
        try {
            this.descriptor = this.classFile.getConstantUtf8Value(this.methodInfo.getDescriptorIndex());
        } catch (ClassFormatException ex) {
            MyLogger.logError(ex.getMessage());
        }
        if((this.methodInfo.getAccessFlags() & MethodInfo.ACC_NATIVE) == MethodInfo.ACC_NATIVE){
            this.localVariable = new Variable[getArgsCount() + 1];
        }else this.localVariable = new Variable[methodInfo.getMaxLocals()];
        //this.localVariable[0] = thisObject;
    }
    
    public byte [] getMethodCode(){
        return this.methodInfo.getByteCode();
    }
    
    public MyInteger popMyIntegerFromStack(){
        try{
            return (MyInteger)this.frameStack.pop();
        }catch(ClassCastException ex){
            ex.printStackTrace();
            MyLogger.logError("ClassCastException: MyInteger is not on the top of the stack.");
        }
        return null;
    }
    
    public MyObject popMyObjectFromStack(){
        try{
            return (MyObject)this.frameStack.pop();
        }catch(ClassCastException ex){
            MyLogger.logError("ClassCastException: MyObject is not on the top of the stack.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public MyArray popMyArrayFromStack(){
        try{
            return (MyArray)this.frameStack.pop();
        }catch(ClassCastException ex){
            MyLogger.logError("ClassCastException: MyArray is not on the top of the stack.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public MyInteger getMyIntegerLocalVar(int index){
        try{
            return (MyInteger)this.localVariable[index];
        }catch(ClassCastException ex){
            MyLogger.logError("ClassCastException: Variable is not MyInteger");
            ex.printStackTrace();
        }
        return null;
    }

    public String getDescriptor() {
        return descriptor;
    }
    
    public int getArgsCount(){
        int retVal = 0;
        String arg = descriptor.substring(descriptor.indexOf("(") + 1);
        arg = arg.substring(0, arg.indexOf(")"));
        String [] splitedArgs = arg.split(";");
        for (String argument : splitedArgs) {
            if(argument.startsWith("L") || argument.startsWith("[")){
                retVal++;
            }else{
                retVal += argument.length();              
            }
        }
        return retVal;
    }
}
