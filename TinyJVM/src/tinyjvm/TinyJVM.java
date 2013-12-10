package tinyjvm;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Stack;
import tinyjvm.execution.ExecutionUnit;
import tinyjvm.structure.ClassHeap;
import tinyjvm.structure.FilePathManager;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;
import tinyjvm.structure.ClassLoader;
import tinyjvm.structure.Frame;
import tinyjvm.structure.ObjectHeap;
import tinyjvm.structure.variable.MyArray;
import tinyjvm.structure.variable.MyString;
/**
 *
 * @author Daniel
 */
public class TinyJVM{

    /**
     * @param args the command line arguments
     */
    
    public static Stack<Frame> frameStack = new Stack();
    
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
        System.out.println("class identifikator: " + filePathManager.getClassIdentifikator(args[0]));
        ClassHeap classHeap = ClassHeap.getInstance();
        ClassFile executeClass = classHeap.addClass(filePathManager.getClassIdentifikator(args[0]));
        
        Frame testFrame = new Frame(executeClass,executeClass.getMethod("main([Ljava/lang/String;)V"));
        //Load arguments
        MyArray argsArray = new MyArray(args.length - 1, "String");
        for (int i = 1; i < args.length; i++) {
            argsArray.addValue(i-1, new MyString(args[i]));
        }
        testFrame.localVariable[0] = argsArray;

        
        frameStack.push(testFrame);
        
        ExecutionUnit.execute(frameStack);
        
        date = new Date();
        System.out.println("Ends at: " + date);
        //classHeap.printClassHeap();
        
    }
    
}
