/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure;

import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.MethodInfo;

/**
 *
 * @author Daniel
 */
public class MethodClassStruct {
    
    public ClassFile classFile;
    public MethodInfo methodInfo;
    
    public MethodClassStruct(ClassFile classFile, MethodInfo methodInfo){
        this.classFile = classFile;
        this.methodInfo = methodInfo;
    }
}
