/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.execution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import library.FileIO;
import tinyjvm.MyLogger;
import tinyjvm.structure.ClassHeap;
import tinyjvm.structure.Frame;
import tinyjvm.structure.MethodArea;
import tinyjvm.structure.MethodClassStruct;
import tinyjvm.structure.ObjectHeap;
import tinyjvm.structure.classfile.AbstractCPInfo;
import tinyjvm.structure.classfile.ClassFile;
import tinyjvm.structure.classfile.MethodInfo;
import tinyjvm.structure.variable.MyArray;
import tinyjvm.structure.variable.MyInteger;
import tinyjvm.structure.variable.MyObject;
import tinyjvm.structure.variable.MyString;
import tinyjvm.structure.variable.Variable;

/**
 *
 * @author Daniel
 */
public class ExecutionUnit {
    
    public static final short CONSTANT_Invoke_Virtual = 0;
    public static final short CONSTANT_Invoke_Special = 1;
    public static final short CONSTANT_Invoke_Static = 2;
    public static final short CONSTANT_Invoke_Interface = 2;
    
    public static ClassHeap classHeap = ClassHeap.getInstance();
    public static ObjectHeap objectHeap = ObjectHeap.getInstance();
    
    public static Variable execute(Stack<Frame> frameStack){
        
        Frame frame = frameStack.lastElement();

        if((frame.methodInfo.getAccessFlags() & MethodInfo.ACC_NATIVE) == MethodInfo.ACC_NATIVE){
            MyLogger.logInfo("EXECUTE NATIVE " + frame.classFile.getThisClassName() + "." + frame.methodInfo.getMethodDeclaration());
            return ExecuteNativeMethod(frame);
        }else MyLogger.logInfo("EXECUTE " + frame.classFile.getThisClassName() + "." + frame.methodInfo.getMethodDeclaration());
        
        final byte[] code = frame.getMethodCode();
        
        MyByteArrayInputStream bai = new MyByteArrayInputStream(code);
     
        //Helpful variables
        int bi;
        short branch;
        MyInteger val1;
        MyInteger val2;
        MyInteger index;
        MyArray array;
        MyObject object;
        Variable var;
        
        while(bai.getPos() < code.length){
            int op = bai.read();
            MyLogger.logInfo("Instruction " + (bai.getPos() - 1) + ": " + Integer.toHexString(op));
            switch(op){
                case opcodeValue.op_bipush:
                    //push a byte onto the stack as an integer value
                    bi = bai.read();
                    MyLogger.logInfo("bipush " + bi);
                    frame.frameStack.push(new MyInteger(bi));
                    break;
                case opcodeValue.op_sipush:
                    //push a short onto the stack
                    bi = getIndex(bai);
                    MyLogger.logInfo("sipush " + bi);
                    frame.frameStack.push(new MyInteger(bi));
                    break;
                case opcodeValue.op_istore:
                    //store int value into variable #index
                    bi = bai.read();
                    MyLogger.logInfo("store int at " + bi); 
                    frame.localVariable[bi] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_istore_0:
                    //store int value into variable 0
                    MyLogger.logInfo("store int at 0"); 
                    frame.localVariable[0] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_istore_1:
                    //store int value into variable 1
                    MyLogger.logInfo("store int at 1"); 
                    frame.localVariable[1] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_istore_2:
                    //store int value into variable 2
                    MyLogger.logInfo("store int at 2"); 
                    frame.localVariable[2] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_istore_3:
                    //store int value into variable 3
                    MyLogger.logInfo("store int at 3"); 
                    frame.localVariable[3] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_iload:
                    //load an int value from a local variable #index
                    bi = bai.read();
                    MyLogger.logInfo("load an int from #" + bi); 
                    frame.frameStack.push(frame.localVariable[bi]);
                    break;
                case opcodeValue.op_iload_0:
                    //load an int value from local variable 0
                    MyLogger.logInfo("load an int from #0");
                    frame.frameStack.push(frame.localVariable[0]);
                    break;
                case opcodeValue.op_iload_1:
                    //load an int value from local variable 1
                    MyLogger.logInfo("load an int from #1");
                    frame.frameStack.push(frame.localVariable[1]);
                    break;
                case opcodeValue.op_iload_2:
                    //load an int value from local variable 2
                    MyLogger.logInfo("load an int from #2");
                    frame.frameStack.push(frame.localVariable[2]);
                    break;
                case opcodeValue.op_iload_3:
                    //load an int value from local variable 3
                    MyLogger.logInfo("load an int from #3");
                    frame.frameStack.push(frame.localVariable[3]);
                    break;
                    
                case opcodeValue.op_iadd:
                    //add two ints
                    val1 = (MyInteger) frame.frameStack.pop();
                    val2 = (MyInteger) frame.frameStack.pop();
                    MyLogger.logInfo("add two ints " + val1.getValue() + "+" + val2.getValue());
                    frame.frameStack.push(val1.add(val2));
                    break;
                case opcodeValue.op_isub:
                    val1 = (MyInteger) frame.frameStack.pop();
                    val2 = (MyInteger) frame.frameStack.pop();
                    MyLogger.logInfo("Substract two ints " + val2.getValue() + "-" + val1.getValue());
                    frame.frameStack.push(val2.sub(val1));
                    break;
                case opcodeValue.op_idiv:
                    val1 = (MyInteger) frame.frameStack.pop();
                    val2 = (MyInteger) frame.frameStack.pop();
                    MyLogger.logInfo("Substract two ints " + val2.getValue() + "/" + val1.getValue());
                    frame.frameStack.push(val2.div(val1));
                    break;
                    
                case opcodeValue.op_aload:
                    //load a reference onto the stack from local variable #index
                    bi = bai.read();
                    MyLogger.logInfo("load a reference from #" + bi);
                    frame.frameStack.push(frame.localVariable[bi]);
                    break;
                case opcodeValue.op_aload_0:
                    //load a reference onto the stack from local variable 0
                    MyLogger.logInfo("load a reference from #0");
                    frame.frameStack.push(frame.localVariable[0]);
                    break;
                case opcodeValue.op_aload_1:
                    //load a reference onto the stack from local variable 1
                    MyLogger.logInfo("load a reference from #1");
                    frame.frameStack.push(frame.localVariable[1]);
                    break;
                case opcodeValue.op_aload_2:
                    //load a reference onto the stack from local variable 2
                    MyLogger.logInfo("load a reference from #2");
                    frame.frameStack.push(frame.localVariable[2]);
                    break;
                case opcodeValue.op_aload_3:
                    //load a reference onto the stack from local variable 3
                    MyLogger.logInfo("load a reference from #3");
                    frame.frameStack.push(frame.localVariable[3]);
                    break;
                case opcodeValue.op_iconst_m1:
                case opcodeValue.op_iconst_0:
                case opcodeValue.op_iconst_1:
                case opcodeValue.op_iconst_2:
                case opcodeValue.op_iconst_3:
                case opcodeValue.op_iconst_4:
                case opcodeValue.op_iconst_5:
                    //load the const int value onto the stack
                    bi = op - 3;
                    MyLogger.logInfo("load " + bi + " value onto the stack");
                    frame.frameStack.push(new MyInteger(bi));
                    break; 
                case opcodeValue.op_ldc:
                    //push a constant #index from a constant pool (String, int or float) onto the stack
                    bi = bai.read();
                    MyLogger.logInfo("Push a constant #" + bi + " from a CP onto the stack");
                    
                    switch(frame.classFile.getCPTag(bi)){
                        case AbstractCPInfo.CONSTANT_String:
                            MyString pushString = new MyString(frame.classFile.getConstantStringValue(bi));
                            frame.frameStack.push(pushString);
                            break;
                        case AbstractCPInfo.CONSTANT_Integer:
                            frame.frameStack.push(new MyInteger(frame.classFile.getConstantIntegerValue(bi)));
                            break;
                        default:
                            MyLogger.logError("Trying to push unsupported type.");
                    }
                    break;
                case opcodeValue.op_putfield:
                    //set field to value in an object objectref, where the field is identified by a field reference index in constant pool (indexbyte1 << 8 + indexbyte2)
                    bi = getIndex(bai);
                    MyLogger.logInfo("Putfield CP[" + bi + "]");
                    var = frame.frameStack.pop();
                    object = frame.popMyObjectFromStack();
                    String fieldName = frame.classFile.getFieldName(bi);
                    object.instanceVar.put(fieldName, var);
                    break;
                case opcodeValue.op_getfield:
                    //get a field value of an object objectref, where the field is identified by field reference in the constant pool index (index1 << 8 + index2)
                    bi = getIndex(bai);
                    object = frame.popMyObjectFromStack();           
                    frame.frameStack.push(object.instanceVar.get(frame.classFile.getFieldName(bi)));
                    break;
                                     
                //*******ARRAY INSTRUCTIONS*******
                case opcodeValue.op_anewarray:
                    //create a new array of references of length count and component type identified by the class reference index (indexbyte1 << 8 + indexbyte2) in the constant pool
                    bi = getIndex(bai);
                    MyLogger.logInfo("Newarray CP[" + bi + "]");
                    String className = frame.classFile.getClassName(bi);
                    frame.frameStack.push(new MyArray(frame.popMyIntegerFromStack().getValue(), className));
                    break;
                case opcodeValue.op_newarray:
                    //create new array with count elements of primitive type identified by atype
                    bi = bai.read();
                    MyLogger.logInfo("Newarray with primitive type " + bi);
                    //type 10 is integer
                    if(bi == 10){
                       frame.frameStack.push(new MyArray(frame.popMyIntegerFromStack().getValue(), "Integer"));
                    }else{
                        MyLogger.logError("Unknown type: " + bi);
                    }                
                    break;
                case opcodeValue.op_arraylength:
                    //get the length of an array
                    MyLogger.logInfo("arraylength: Get array length");
                    array = frame.popMyArrayFromStack();
                    frame.frameStack.push(new MyInteger(array.getLength()));
                    break;
                case opcodeValue.op_iastore:
                    //arrayref, index, value →	: store an int into an array
                    val1 = frame.popMyIntegerFromStack();
                    index = frame.popMyIntegerFromStack();
                    array = frame.popMyArrayFromStack();
                    MyLogger.logInfo("Store int value " + val1.getValue() + " to index " + index.getValue());
                    array.addValue(index.getValue(), val1);
                    break;
                case opcodeValue.op_aastore:
                    //arrayref, index, value →	: store into a reference in an array
                    var = frame.frameStack.pop();
                    index = frame.popMyIntegerFromStack();
                    array = frame.popMyArrayFromStack();
                    MyLogger.logInfo("Store Variable value of type " + var.getType() + " to index " + index.getValue());
                    array.addValue(index.getValue(), var);
                    break;
                case opcodeValue.op_aaload:
                    //load onto the stack a reference from an array
                    MyLogger.logInfo("Load Variable from an array");
                    index = frame.popMyIntegerFromStack();
                    array = frame.popMyArrayFromStack();
                    frame.frameStack.push(array.getValue(index.getValue()));
                    break;
                case opcodeValue.op_iaload:
                    //load an int from an array
                    MyLogger.logInfo("Load int value from an array");
                    index = frame.popMyIntegerFromStack();
                    array = frame.popMyArrayFromStack();
                    frame.frameStack.push(array.getMyIntegerValue(index.getValue()));
                    break;
                case opcodeValue.op_astore:
                    //objectref → : store a reference into a local variable #index
                    bi = bai.read();
                    MyLogger.logInfo("Store ref into local variable #" + bi);
                    frame.localVariable[bi] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_astore_0:
                    //objectref → : store a reference into local variable 0
                    MyLogger.logInfo("Store ref into local variable #0");
                    frame.localVariable[0] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_astore_1:
                    //objectref → : store a reference into local variable 1
                    MyLogger.logInfo("Store ref into local variable #1");
                    frame.localVariable[1] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_astore_2:
                    //objectref → : store a reference into local variable 2
                    MyLogger.logInfo("Store ref into local variable #2");
                    frame.localVariable[2] = frame.frameStack.pop();
                    break;
                case opcodeValue.op_astore_3:
                    //objectref → : store a reference into local variable 3
                    MyLogger.logInfo("Store ref into local variable #3");
                    frame.localVariable[3] = frame.frameStack.pop();
                    break;
                    
                //*******INVOKE INSTRUCTIONS*******
                case opcodeValue.op_invokespecial:
                    //invoke instance method on object objectref, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
                    bi = getIndex(bai);
                    MyLogger.logInfo("invokespecial CP[" + bi + "]");
                    ExecuteInvokeSpecial(frameStack, bi);
                    break;
                case opcodeValue.op_invokevirtual:
                    //invoke virtual method on object objectref, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
                    bi = getIndex(bai);
                    MyLogger.logInfo("invokevirtual CP[" + bi + "]");
                    ExecuteInvokeVirtual(frameStack, bi, CONSTANT_Invoke_Virtual);
                    break;
                case opcodeValue.op_invokestatic:
                    //invoke a static method, where the method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
                    bi = getIndex(bai);
                    MyLogger.logInfo("invokestatic CP[" + bi + "]");
                    ExecuteInvokeVirtual(frameStack, bi, CONSTANT_Invoke_Static);
                    break;
                case opcodeValue.op_new:
                    //create new object of type identified by class reference in constant pool index (indexbyte1 << 8 + indexbyte2)
                    bi = getIndex(bai);
                    MyLogger.logInfo("create new object CP[" + bi + "]");
                    createNewObject(frame, bi);
                    break; 
                case opcodeValue.op_dup:
                    //duplicate the value on top of the stack
                    MyLogger.logInfo("Duplicate top of the stack.");
                    frame.frameStack.push(frame.frameStack.lastElement());
                    break;
                    
                 //*******IF INSTRUCTIONS*******
                 case opcodeValue.op_if_icmple:
                    //if value1 is less than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    //TODO udelat jednu metodu z if_icmple a if_icmpge
                    branch = (short) getIndex(bai);
                    val1 = frame.popMyIntegerFromStack();
                    val2 = frame.popMyIntegerFromStack();
                    MyLogger.logInfo("If " + val2.getValue() + " <= " + val1.getValue() + " goto " + branch);
                    if(val1.getValue() >= val2.getValue()){
                        //set stream to proper position
                        bi = bai.getPos();
                        bai.reset();
                        bai.skip(branch + bi - 3);
                    }
                    break;
                case opcodeValue.op_if_icmpge:
                    //if value1 is greater than or equal to value2, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    branch = (short) getIndex(bai);
                    val2 = frame.popMyIntegerFromStack();
                    val1 = frame.popMyIntegerFromStack();
                    MyLogger.logInfo("If " + val2.getValue() + " <= " + val1.getValue() + " goto " + branch);
                    if(val1.getValue() >= val2.getValue()){
                        //set stream to proper position
                        bi = bai.getPos();
                        bai.reset();
                        bai.skip(branch + bi - 3);
                    }
                    break;
                case opcodeValue.op_if_icmpeq:
                    //if ints are equal, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    branch = (short) getIndex(bai);
                    val1 = frame.popMyIntegerFromStack();
                    val2 = frame.popMyIntegerFromStack();
                    MyLogger.logInfo("If " + val1.getValue() + " == " + val2.getValue() + " goto " + branch);
                    if(val1.getValue() == val2.getValue()){
                        //set stream to proper position
                        bi = bai.getPos();
                        bai.reset();
                        bai.skip(branch + bi - 3);
                    }
                    break;
                case opcodeValue.op_ifeq:
                    //if value is 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    branch = (short) getIndex(bai);
                    val1 = frame.popMyIntegerFromStack();
                    MyLogger.logInfo("If " + val1.getValue() + " == " + 0 + " goto " + branch);
                    if(val1.getValue() == 0){
                        //set stream to proper position
                        bi = bai.getPos();
                        bai.reset();
                        bai.skip(branch + bi - 3);
                    }
                    break;
                case opcodeValue.op_ifle:
                    //if value is less than or equal to 0, branch to instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    branch = (short) getIndex(bai);
                    val1 = frame.popMyIntegerFromStack();
                    MyLogger.logInfo("If " + val1.getValue() + " <= " + 0 + " goto " + branch);
                    if(val1.getValue() <= 0){
                        //set stream to proper position
                        bi = bai.getPos();
                        bai.reset();
                        bai.skip(branch + bi - 3);
                    }
                    break;
                    
                case opcodeValue.op_goto:
                    //goes to another instruction at branchoffset (signed short constructed from unsigned bytes branchbyte1 << 8 + branchbyte2)
                    branch = (short)getIndex(bai);
                    bi = bai.getPos();
                    bai.reset();
                    bai.skip(bi + branch - 3);
                    MyLogger.logInfo("Move on " + branch + " to " + bai.getPos());
                    break;
                case opcodeValue.op_pop:
                    //Pop value from stack
                    frame.frameStack.pop();
                    break;
                case opcodeValue.op_iinc:
                    //increment local variable #index by signed byte const
                    bi = bai.read();
                    int incr = bai.read();
                    MyLogger.logInfo("Increment local variable [" + bi + "] by " + incr);
                    val1 = frame.getMyIntegerLocalVar(bi);
                    val1.increment(incr);
                    break;
                    
                //*******RETURN STATEMENTS******
                case opcodeValue.op_return:
                    //return void from method
                    MyLogger.logInfo("return void");
                    return null;
                case opcodeValue.op_ireturn:
                    //return an integer from a method
                    MyLogger.logInfo("return int");
                    return frame.frameStack.pop();
                case opcodeValue.op_areturn:
                    //return an integer from a method
                    MyLogger.logInfo("return reference");
                    return frame.frameStack.pop();
                default:
                    MyLogger.logError(frame.methodName + ":Unsupported instruction op: " + Integer.toHexString(op) + " at " + bai.getPos());
                    System.exit(1);
                    
            }
        }
        return null;
    }

    private static void ExecuteInvokeSpecial(Stack<Frame> frameStack, int methodCPIndex) {
        Frame actualFrame = frameStack.lastElement();
        String methodClass = actualFrame.classFile.getMethodsClassName(methodCPIndex);
   
        //add class file representation to ClassHeap
        ClassFile classFile = classHeap.addClass(methodClass); 
        ExecuteInvokeVirtual(frameStack, methodCPIndex, CONSTANT_Invoke_Special);
    }
    
    private static void ExecuteInvokeVirtual(Stack<Frame> frameStack, int methodCPIndex, int type) {
        //TODO zakomponovat do MethodInfo tridy
        Frame actualFrame = frameStack.lastElement();
        String methodName = actualFrame.classFile.getMethodName(methodCPIndex);
        String methodDescription = actualFrame.classFile.getMethodDescription(methodCPIndex);
        String methodClass = actualFrame.classFile.getMethodsClassName(methodCPIndex);
        MyLogger.logInfo("Invoke method " + methodClass + "." + methodName + methodDescription);
        //add class file representation to ClassHeap
        ClassFile classFile = classHeap.getClass(methodClass);
        if(classFile == null){
            classFile = classHeap.addClass(methodClass);
            if(classFile == null){ MyLogger.logError(methodClass + " class doesn't exist.");}
        }
        //find method
        MethodClassStruct methodClassInfo = findMethod(classFile, methodName + methodDescription);
        Frame invokeFrame = new Frame(methodClassInfo.classFile, methodClassInfo.methodInfo);
        
        //add method arguments to invoke frame locals
        String descriptor = invokeFrame.getDescriptor();
        descriptor = processMethodDescriptor(descriptor);
        int index = descriptor.length();
        for (char c : descriptor.toCharArray()) {
            switch (c) {
                case 'L':
                case '[':
                case 'I':
                case 'S':
                case 'Z':
                case 'B':
                    invokeFrame.localVariable[index] = actualFrame.frameStack.pop();
                    break;
                default:
                    MyLogger.logError("Type " + c + " is not supported.");
            }
            index--;
        }
        
        if (type == CONSTANT_Invoke_Virtual || type == CONSTANT_Invoke_Special) {
            //invokeFrame.localVariable[0] = actualFrame.popMyObjectFromStack();
            invokeFrame.localVariable[0] = actualFrame.frameStack.pop();
        }else if(type == CONSTANT_Invoke_Static){
            invokeFrame.localVariable[0] = null;
        }
        
        frameStack.push(invokeFrame);
        Variable retVal = ExecutionUnit.execute(frameStack);
        frameStack.pop();
        MyLogger.logInfo("Continuing of execution method " + actualFrame.classFile.getThisClassName() + "." + actualFrame.methodInfo.getMethodDeclaration());
        if(retVal != null){
            MyLogger.logInfo("Add return value to the stack.");
            actualFrame.frameStack.push(retVal);
        }
    }
    
    private static String processMethodDescriptor(String desc){
        String retVal = "";
        String arg = desc.substring(desc.indexOf("(") + 1);
        arg = arg.substring(0, arg.indexOf(")"));
        List <String> splitedArgs = Arrays.asList(arg.split(";"));
        Collections.reverse(splitedArgs);
        for (String argument : splitedArgs) {
            if(argument.startsWith("L") || argument.startsWith("[")){
                retVal = retVal.concat(argument.substring(0, 1));
            }else{
                retVal = retVal.concat(new StringBuffer(argument).reverse().toString());              
            }
        }
        return retVal;
    }
    
    private static void createNewObject(Frame frame, int classCPIndex){
        String className = frame.classFile.getClassName(classCPIndex);
        ClassFile classFile = classHeap.addClass(className);
        frame.frameStack.push(objectHeap.createObject(classFile));
        MyLogger.logInfo("Instance of " + className + " was successfuly added to frame stack.");
    }
    
    private static int getIndex(MyByteArrayInputStream bai){
        int fb = bai.read();
        int sb = bai.read();
        return (fb << 8) + sb;
    }

    private static Variable ExecuteNativeMethod(Frame frame) {
        //TODO - add logs
        String methodDescriptor = frame.classFile.getThisClassName() + "." + frame.methodName + frame.getDescriptor();
        Variable thisObject = frame.localVariable[0];
        MyObject myObject = null;
        MyString myString = null;
        if(thisObject instanceof MyObject){
            myObject = (MyObject)thisObject;
        }else if(thisObject instanceof MyString){
            myString = (MyString) thisObject;
        }

        if(methodDescriptor.equals("java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
        {
            MyString appendString = (MyString)frame.localVariable[1];
            if(myObject.instanceVar.containsKey("String")){
                MyString tmpString = (MyString) myObject.instanceVar.get("String");
                myObject.instanceVar.put("String",new MyString(tmpString.getValue() + appendString.getValue()));
            }else myObject.instanceVar.put("String",appendString);
            return myObject;
        }else if(methodDescriptor.equals("java/lang/StringBuilder.append(I)Ljava/lang/StringBuilder;"))
        {
            MyInteger appendInteger = (MyInteger)frame.localVariable[1];
            if(myObject.instanceVar.containsKey("String")){
                MyString tmpString = (MyString) myObject.instanceVar.get("String");
                myObject.instanceVar.put("String",new MyString(tmpString.getValue() + appendInteger.getValue()));
            }else myObject.instanceVar.put("String",new MyString(appendInteger.toString())); 
            return myObject;
        }else if(methodDescriptor.equals("java/lang/StringBuilder.toString()Ljava/lang/String;"))
        {
            return myObject.instanceVar.get("String");
            
        }else if(methodDescriptor.contains("print(Ljava/lang/String;)V"))
        {
            System.out.println(frame.localVariable[1].toString());
        }else if(methodDescriptor.contains("readFile(Ljava/lang/String;)Ljava/lang/String;"))
        {
            MyLogger.logInfo("Read file: " + frame.localVariable[1].toString());
            String str = FileIO.readFile(frame.localVariable[1].toString());
            return new MyString(str);
        }else if(methodDescriptor.contains("writeToFile(Ljava/lang/String;Ljava/lang/String;)V"))
        {
            String filePath = frame.localVariable[1].toString();
            String text = frame.localVariable[2].toString();
            MyLogger.logInfo("Write to file " + filePath + " \"" + text + "\".");
            FileIO.writeToFile(filePath, text);
        }else if(methodDescriptor.equals("java/lang/String.split(Ljava/lang/String;)[Ljava/lang/String;")){
            String str = frame.localVariable[1].toString();
            MyLogger.logInfo("Split string: " + myString.toString());
            String [] splitString = myString.toString().split(str);
            MyArray retVal = new MyArray(splitString.length, "String");
            for (int i = 0; i < splitString.length; i++) {
                retVal.addValue(i, new MyString(splitString[i]));
            }
            return retVal;
        }else if(methodDescriptor.equals("java/lang/Integer.valueOf(Ljava/lang/String;)Ljava/lang/Integer;")){
            String str = frame.localVariable[1].toString();
            MyLogger.logInfo("Convert String \"" + str + "\" to MyInteger value");
            return new MyInteger(Integer.valueOf(str));
        }else if(methodDescriptor.equals("java/lang/Integer.intValue()I")){
            return frame.localVariable[0];
        }
        
        return null;
    }

    private static MethodClassStruct findMethod(ClassFile classFile, String methodDeclaration) {
        if(classFile == null){
            MyLogger.logError("Method " + methodDeclaration + " doesn't exist.");
            System.exit(404);
        }
        String methKey = classFile.getThisClassName() + "." + methodDeclaration;
        MethodInfo foundMethod = MethodArea.methodArea.get(methKey);
        if(foundMethod == null){
            ClassFile superClass = ClassHeap.getInstance().addClass(classFile.getSuperClassName());
            return findMethod(superClass, methodDeclaration);
        }     
        return new MethodClassStruct(classFile, foundMethod);
    }

        /*
    private static String getMethodIdentificator(ClassFile classFile, String methodDeclaration){
        return classFile.getThisClassName() + "." + methodDeclaration;
    }
                */
}
