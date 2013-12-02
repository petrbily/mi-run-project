package tinyjvm.structure.variable;

/**
 *
 * @author Daniel
 */
public abstract class Variable {
    
    public String getType(){
        return "Unknown";
    }
    
    @Override
    public abstract String toString();
}
