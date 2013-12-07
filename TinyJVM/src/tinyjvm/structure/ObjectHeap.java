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
    
    private SecureRandom random;
    public HashMap<String,MyObject> objectHeap;
    private static ObjectHeap instance;
    
    private ObjectHeap(){
        this.objectHeap = new HashMap();
        this.random = new SecureRandom();
    }
    
    //Singleton
    public static ObjectHeap getInstance() {
         if (instance == null) {
             instance = new ObjectHeap();
         }
         return instance;
     }
    
    public MyObject createObject(ClassFile classFile){
        
        String randID = getReference();
        while(objectHeap.containsKey(randID)){
            randID = getReference();
        }
        
        MyObject obj = new MyObject(randID);
        objectHeap.put(randID, obj);
        
        return obj;
    }
    
    public String getReference(){
        return new BigInteger(130, random).toString(32);
    }
}
