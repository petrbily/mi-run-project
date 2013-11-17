package tinyjvm.structure.variable;

import tinyjvm.structure.classfile.FieldInfo;

/**
 *
 * @author Daniel
 */
public class Variable {
    
    private FieldInfo fieldInfo;
    
    public Variable(FieldInfo fi){
        this.fieldInfo = fi;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
    
    public String getType(){
        return "Unknown";
    }
}
