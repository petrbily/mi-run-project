/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.execution;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import tinyjvm.structure.Frame;

/**
 *
 * @author Daniel
 */
public class ExecutionUnit {
    
    
    public static void execute(Frame frame) throws IOException{
    
        final byte[] code = frame.getMethodCode();
        
        MyByteArrayInputStream bai = new MyByteArrayInputStream(code);
        //DataInputStream dis = new DataInputStream(bai);
     
        int bi;
        
        while(bai.getPos() < code.length){
            int op = bai.read();
            switch(op){
                case opcodeValue.op_bipush:
                    //push a byte onto the stack as an integer value
                    bi = bai.read();
                    frame.localStack.push(bi);
                    break;
                case opcodeValue.op_istore:
                    //store int value into variable #index
                    bi = bai.read();
                    frame.localVariable[bi] = frame.localStack.pop();
                    break;
                case opcodeValue.op_istore_0:
                    //store int value into variable 0
                    frame.localVariable[0] = frame.localStack.pop();
                    break;
                case opcodeValue.op_istore_1:
                    //store int value into variable 1
                    frame.localVariable[1] = frame.localStack.pop();
                    break;
                case opcodeValue.op_istore_2:
                    //store int value into variable 2
                    frame.localVariable[2] = frame.localStack.pop();
                    break;
                case opcodeValue.op_istore_3:
                    //store int value into variable 3
                    frame.localVariable[3] = frame.localStack.pop();
                    break;
                case opcodeValue.op_iload:
                    //load an int value from a local variable #index
                    bi = bai.read();
                    frame.localStack.push(frame.localVariable[bi]);
                    break;
                case opcodeValue.op_iload_0:
                    //load an int value from local variable 0
                    frame.localStack.push(frame.localVariable[0]);
                    break;
                case opcodeValue.op_iload_1:
                    //load an int value from local variable 1
                    frame.localStack.push(frame.localVariable[1]);
                    break;
                case opcodeValue.op_iload_2:
                    //load an int value from local variable 2
                    frame.localStack.push(frame.localVariable[2]);
                    break;
                case opcodeValue.op_iload_3:
                    //load an int value from local variable 3
                    frame.localStack.push(frame.localVariable[3]);
                    break;
                case opcodeValue.op_iadd:
                    //add two ints
                    int a = frame.localStack.pop();
                    int b = frame.localStack.pop();
                    frame.localStack.push(a + b);
                    break;
                    
                    
                case opcodeValue.op_return:
                    //return void from method
                    return;
                
                default:
                    System.err.println("Unsupported instruction.");
                    
            }
        }
    }
}
