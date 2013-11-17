package tinyjvm.structure;

import tinyjvm.structure.variable.MyObject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import tinyjvm.structure.classfile.ClassFile;

/**
 *
 * @author Daniel
 */
public class ObjectHeap {
    
    private SecureRandom random = new SecureRandom();
    public HashMap<String,MyObject> objectHeap = new HashMap<String,MyObject>();
    
    
    public MyObject createObject(ClassFile classFile){
        
        String randID = getReference();
        while(objectHeap.containsKey(randID)){
            randID = getReference();
        }
        
        MyObject obj = new MyObject(null, classFile, randID); //TODO - solve first param FieldInfo
        objectHeap.put(randID, obj);
        
        return obj;
    }
    
    public String getReference(){
        return new BigInteger(130, random).toString(32);
    }
}
