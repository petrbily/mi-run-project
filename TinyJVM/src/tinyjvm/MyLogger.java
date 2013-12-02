/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm;

import java.util.Date;


/**
 *
 * @author Daniel
 */
public class MyLogger{
    
   //private static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(MyLogger.class.getName());
    public static Date date;
    public static boolean DEBUG = false;
    
    public static void logInfo(String msg) {
        if(DEBUG){
            date = new Date();
            System.out.println("DEBUG " + date.toString() + ": " + msg);
        }
    }

    public static void logError(String msg) {
        date = new Date();
        System.err.println("ERROR " + date.toString() + ": " + msg);
        
        //TODO remove system exit
        System.exit(1);
    }

    
}
