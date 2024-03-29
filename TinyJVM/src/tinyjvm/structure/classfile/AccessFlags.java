/*
 * AccessFlags.java    9:27 PM, August 7, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

/**
 * Access Flag of a {@code class} or {@code interface}.
 * It is the {@code access_flags} in {@code ClassFile} structure.
 * <p>
 * The access flag is a mask combination of the following flags:
 *
 * <pre>
 *    Flag Name      Value     Interpretation
 *    ACC_PUBLIC     0x0001    Declared public; may be accessed from outside its package.
 *    ACC_FINAL      0x0010    Declared final; no subclasses allowed.
 *    ACC_SUPER      0x0020    Treat superclass methods specially when invoked by the invokespecial instruction.
 *    ACC_INTERFACE  0x0200    Is an interface, not a class.
 *    ACC_ABSTRACT   0x0400    Declared abstract; may not be instantiated.
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see ClassFile#getAccessFlags()
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#74353">
 * VM Spec: The ClassFile Structure
 * </a>
 */

public class AccessFlags {

    /**
     * Value for access flag {@code ACC_PUBLIC}.
     */
    public static final int ACC_PUBLIC = 0x0001;
    /**
     * Value for access flag {@code ACC_FINAL}.
     */
    public static final int ACC_FINAL = 0x0010;
    /**
     * Value for access flag {@code ACC_SUPER}.
     */
    public static final int ACC_SUPER = 0x0020;
    /**
     * Value for access flag {@code ACC_INTERFACE}.
     */
    public static final int ACC_INTERFACE = 0x0200;
    /**
     * Value for access flag {@code ACC_ABSTRACT}.
     */
    public static final int ACC_ABSTRACT = 0x0400;
    
    public int value;

    AccessFlags(int value){
           this.value = value;
    }

    /**
     * Generate the modifier of a {@code class} or {@code interface} from the access flag value.
     *
     * @return A string for modifier
     */
    public String getModifiers() {
        final StringBuilder sb = new StringBuilder(25);
        Boolean isFirstModifier = true;

        if ((this.value & AccessFlags.ACC_PUBLIC) > 0) {
            sb.append("public ");
            isFirstModifier = false;
        }
        if ((this.value & AccessFlags.ACC_FINAL) > 0) {
            if (isFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("final ");
            isFirstModifier = false;
        }
        if ((this.value & AccessFlags.ACC_SUPER) > 0) {
            if (isFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("super ");
            isFirstModifier = false;
        }
        if ((this.value & AccessFlags.ACC_INTERFACE) > 0) {
            if (isFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("interface ");
            isFirstModifier = false;
        }
        if ((this.value & AccessFlags.ACC_ABSTRACT) > 0) {
            if (isFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("abstract ");
            isFirstModifier = false;
        }


        return sb.toString();
    }

    /**
     * Display a string for the raw format of the internal values of this access flag object.
     *
     * @return A string representing this object
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(25);

        sb.append(String.format("%04X  (", this.value));

        if ((this.value & AccessFlags.ACC_PUBLIC) > 0) {
            sb.append("public ");
        }
        if ((this.value & AccessFlags.ACC_FINAL) > 0) {
            sb.append("final ");
        }
        if ((this.value & AccessFlags.ACC_SUPER) > 0) {
            sb.append("super ");
        }
        if ((this.value & AccessFlags.ACC_INTERFACE) > 0) {
            sb.append("interface ");
        }
        if ((this.value & AccessFlags.ACC_ABSTRACT) > 0) {
            sb.append("abstract ");
        }

        sb.append(")");

        return sb.toString();
    }
}
