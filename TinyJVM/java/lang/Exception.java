package java.lang;

/**
 *
 * @author Daniel
 */
public class Exception {
    
    private String message;
    
    public Exception(){
        this.message = null;
    }
    
    public Exception(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
