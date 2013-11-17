package tinyjvm;

import java.io.File;
import java.io.IOException;
import tinyjvm.structure.ClassHeap;
import tinyjvm.structure.FilePathManager;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;
import tinyjvm.structure.ClassLoader;
import tinyjvm.structure.ObjectHeap;
/**
 *
 * @author Daniel
 */
public class TinyJVM{

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException, ClassFormatException {
        //TODO kontrola argumentu
        
        if(args.length < 1){
            System.out.println("Invalid arguments");
        }
        
        File mainFile = new File(args[0]);
        FilePathManager filePathManager = new FilePathManager(mainFile);
        System.out.println("rootPath: " + filePathManager.getRootPath());
        
        ClassHeap classHeap = new ClassHeap(filePathManager);
        ClassFile rootClass = ClassLoader.getClassFile(args[0]);
        classHeap.addRecursivelyFromRoot(rootClass);
        //classHeap.addClass("runtest/Clovek");
        
        classHeap.printClassHeap();
        
        ObjectHeap oh = new ObjectHeap();
        
    }
    
}
