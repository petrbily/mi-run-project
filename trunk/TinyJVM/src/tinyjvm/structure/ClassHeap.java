/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure;

import java.io.IOException;
import java.util.HashMap;
import tinyjvm.MyLogger;
import tinyjvm.execution.ExecutionUnit;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.ClassFormatException;

/**
 *
 * @author Daniel
 */
public class ClassHeap {
    
    private static ClassHeap instance;
    public HashMap<String,ClassFile> classHeap;
    
    private ClassHeap(){
        this.classHeap = new HashMap();
    }
    
    //Singleton
    public static ClassHeap getInstance() {
         if (instance == null) {
             instance = new ClassHeap();
         }
         return instance;
     }
    
    public ClassFile addClass(String className){
        MyLogger.logInfo("Add class " + className + " to ClassHeap.");
        if(classHeap.containsKey(className)){
            MyLogger.logInfo("Classfile " + className + " is already on the heap");
            return classHeap.get(className);
        }
        String absolutePath = FilePathManager.getInstance().getAbsolutePath(className);
        MyLogger.logInfo("Load classfile from file " + absolutePath);
        ClassFile newClassFile = ClassLoader.getClassFile(absolutePath);
        MyLogger.logInfo("Classfile " + className + " was successfuly loaded");
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
    
    public ClassFile getClass(String key){
        return classHeap.get(key);
    }
}
