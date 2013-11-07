package tinyjvm.structure;

import java.io.File;
import java.io.IOException;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;

/**
 *
 * @author Daniel
 */
public class ClassLoader {
    
    public static ClassFile getClassFile(String path) throws IOException, ClassFormatException{
        File file = new File(path);
        return new ClassFile(ClassReader.readClassFile(file));
    }
}
