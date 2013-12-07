package tinyjvm;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import tinyjvm.execution.ExecutionUnit;
import tinyjvm.structure.ClassHeap;
import tinyjvm.structure.FilePathManager;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;
import tinyjvm.structure.ClassLoader;
import tinyjvm.structure.Frame;
import tinyjvm.structure.ObjectHeap;
import tinyjvm.structure.VMStack;
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
        
        Date date = new Date();
        System.out.println("Starts at: " + date);
        File mainFile = new File(args[0]);
        FilePathManager filePathManager = FilePathManager.getInstance();
        filePathManager.setRootPath(mainFile);
        System.out.println("rootPath: " + filePathManager.getRootPath());
        
        ClassHeap classHeap = ClassHeap.getInstance();
        ClassFile rootClass = ClassLoader.getClassFile(args[0]);
        classHeap.addClass("knapsacksolver/KnapsackSolver");
        //classHeap.addClass("runtest/Clovek");
        ObjectHeap objectHeap = ObjectHeap.getInstance();
        ClassFile testClass = classHeap.getClass("knapsacksolver/KnapsackSolver");
        
        Frame testFrame = new Frame(testClass,testClass.getMethod("main([Ljava/lang/String;)V"));
        testFrame.localVariable[0] = objectHeap.createObject(testClass);
        VMStack vmStack = new VMStack();
        vmStack.frameStack.push(testFrame);
        
        ExecutionUnit.execute(vmStack.frameStack);
        
        date = new Date();
        System.out.println("Ends at: " + date);
        //classHeap.printClassHeap();
        
    }
    
}
