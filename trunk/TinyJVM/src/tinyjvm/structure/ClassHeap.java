/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure;

import java.io.IOException;
import java.util.HashMap;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;

/**
 *
 * @author Daniel
 */
public class ClassHeap {
    
    private HashMap<String,ClassFile> classHeap;
    private FilePathManager filePathManager;
    
    public ClassHeap(FilePathManager filePathManager){
        //TODO make it expander, not fixied
        classHeap = new HashMap<String,ClassFile>();
        this.filePathManager = filePathManager;
    }
    
    public ClassFile addClass(String className) throws IOException, ClassFormatException{
        //TODO create native method for java/...
        System.out.println("add class: " + className);
        if(className.contains("java")) return null;
        String absolutePath = filePathManager.getAbsolutePath(className);
        System.out.println("absolute class path: " + absolutePath);
        ClassFile newClassFile = ClassLoader.getClassFile(absolutePath);
        classHeap.put(className, newClassFile);
        return newClassFile;
    }
    
    public void addRecursivelyFromRoot(ClassFile classFile) throws IOException, ClassFormatException{
        if(classFile == null) return;
        System.out.println("class name " + classFile.getThisClassName());
        
        classHeap.put(classFile.getThisClassName(), classFile);
        for(String linkClass : classFile.getClassNamesFromCP()){
            if(classHeap.containsKey(linkClass)){
                System.out.println("ClassHeap already contain " + linkClass + ".class");
                continue;
            }
            ClassFile addClass = this.addClass(linkClass);
            System.out.println("Link Class: " + linkClass);
            addRecursivelyFromRoot(addClass);
        }
    }

    public HashMap<String, ClassFile> getClassHeap() {
        return classHeap;
    }
    
    public void printClassHeap(){
        System.out.println("CLASS HEAP:");
        for(String key : classHeap.keySet()){
            System.out.println("Key: " + key);
        }
    }
}
