package tinyjvm.structure;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;
import tinyjvm.structure.classfile.MethodInfo;

/**
 *
 * @author Daniel
 */
public class ClassLoader {
    
    public static ClassFile getClassFile(String path){
        File file = new File(path);
        try {
            ClassFile newClassFile = new ClassFile(ClassReader.readClassFile(file));
            for(MethodInfo meth : newClassFile.getMethods()){
                MethodArea.methodArea.put(newClassFile.getThisClassName() + "." + meth.getMethodDeclaration(),meth);
            }
            return new ClassFile(ClassReader.readClassFile(file));
        } catch (IOException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassFormatException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
