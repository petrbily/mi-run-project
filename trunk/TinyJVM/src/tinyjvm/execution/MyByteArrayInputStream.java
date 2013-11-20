/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.execution;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Daniel
 */
public class MyByteArrayInputStream extends ByteArrayInputStream{

    public MyByteArrayInputStream(byte[] buf) {
        super(buf);
    }
    
    public int getPos(){
        return this.pos;
    }
    
}
