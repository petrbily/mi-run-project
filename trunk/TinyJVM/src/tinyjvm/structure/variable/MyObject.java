package tinyjvm.structure.variable;

import java.util.ArrayList;
import java.util.HashMap;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.FieldInfo;


/**
 *
 * @author Daniel
 */
public class MyObject extends Variable{
    
    public String id;
    public HashMap<String,Variable> instanceVar = new HashMap<String,Variable>();
    public ClassFile classFile;
    
    public MyObject(FieldInfo info, ClassFile classFile, String id){
        super(info);
        this.id = id;
        this.classFile = classFile;
        //TODO execute init class

    }
    
    
    @Override
    public String getType(){
        return "Object";
    }

    //TODO init at runtime
    private void initInstanceVariables() {
        int field_count = classFile.getFieldCount().getValue();
        
        for(FieldInfo fi : classFile.getFields()){
            
        }
        
    }

}
