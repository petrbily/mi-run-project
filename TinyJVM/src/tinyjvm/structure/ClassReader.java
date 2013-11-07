/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class ClassReader {
    
    
    /**
     * Read class byte array from the {@code class} file {@code file}
     *
     * @param file The class file
     * @return Byte array of the class file, or {@code null} if error happened.
     */
    public static byte[] readClassFile(final File file) {
        FileInputStream input = null;

        final long fileLength = file.length();
        final int fileLengthInt = (int) (fileLength % Integer.MAX_VALUE);

        byte[] contents = new byte[fileLengthInt];
        final ByteBuffer byteBuf = ByteBuffer.allocate(contents.length);
        int bytesRead;
        int bytesAll = 0;
        

        try {
            input = new FileInputStream(file);

            while(true){
                bytesRead = input.read(contents);
                if (bytesRead != -1){
                    byteBuf.put(contents, 0, bytesRead);
                    bytesAll += bytesRead;
                }else{
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (bytesAll == fileLengthInt){
            return byteBuf.array();
        }else{
            return null;
        }
    }
}
