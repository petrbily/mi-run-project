package tinyjvm.structure.variable;

import java.util.HashMap;
import tinyjvm.MyLogger;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.FieldInfo;


/**
 *
 * @author Daniel
 */
public class MyObject extends Variable{
    
    public String id;
    public String className;
    public HashMap<String,Variable> instanceVar = new HashMap<String,Variable>();
    //public MyObject superObject;
    
    /*
    public MyObject(String id){
        this.id = id;
    }
    */
    
    public MyObject(String id, String className){
        this.id = id;
        this.className = className;
    }
    
    public MyObject(String id, String className, HashMap<String,Variable> instanceVar){
        this(id, className);
        this.instanceVar = instanceVar;
    }
    
    @Override
    public String getType(){
        return "Object";
    }
    
    @Override
    public String toString(){
        return id;
    }
    
    public void putInstanceVar(String key, Variable var){
        this.instanceVar.put(key, var);
    }
    
    /*
    public String putInstanceVarWithCPIndex(int index, Variable var){
        String key = this.classFile.getFieldName(index);
        this.instanceVar.put(key, var);
        MyLogger.logInfo("Put field of name " + key + " to object variables");
        return key;
    }
    
    public Variable getInstanceVarWithCPIndex(int index){
        String key = this.classFile.getFieldName(index);
        Variable retVar = this.instanceVar.get(key);
        if(retVar == null){
            MyLogger.logError("Variable " + key + " is not store in the instance variable heap.");
        }
        MyLogger.logInfo("Get field of name " + key);
        return retVar;
    }
    */

}
