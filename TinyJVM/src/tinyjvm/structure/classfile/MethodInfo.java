/*
 * MethodInfo.java    4:01 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * {@code Method} of a class or interface. The {@code Method} structure has the following format:
 *
 * <pre>
 *    method_info {
 *        u2 access_flags;
 *        u2 name_index;
 *        u2 descriptor_index;
 *        u2 attributes_count;
 *        attribute_info attributes[attributes_count];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1513">
 * VM Spec:  Methods
 * </a>
 */
public class MethodInfo extends ClassComponent {

    int access_flags;
    int name_index;
    int descriptor_index;
    int attributes_count;
    AttributeInfo[] attributes;
    AttributeCode code;
    /**
     * Value for access flag {@code ACC_PUBLIC} for a {@code Method}.
     */
    public static final int ACC_PUBLIC = 0x0001;
    /**
     * Value for access flag {@code ACC_PRIVATE} for a {@code Method}.
     */
    public static final int ACC_PRIVATE = 0x0002;
    /**
     * Value for access flag {@code ACC_PROTECTED} for a {@code Method}.
     */
    public static final int ACC_PROTECTED = 0x0004;
    /**
     * Value for access flag {@code ACC_STATIC} for a {@code Method}.
     */
    public static final int ACC_STATIC = 0x0008;
    /**
     * Value for access flag {@code ACC_FINAL} for a {@code Method}.
     */
    public static final int ACC_FINAL = 0x0010;
    /**
     * Value for access flag {@code ACC_SYNCHRONIZED} for a {@code Method}.
     */
    public static final int ACC_SYNCHRONIZED = 0x0020;
    /**
     * Value for access flag {@code ACC_NATIVE} for a {@code Method}.
     */
    public static final int ACC_NATIVE = 0x0100;
    /**
     * Value for access flag {@code ACC_ABSTRACT} for a {@code Method}.
     */
    public static final int ACC_ABSTRACT = 0x0400;
    /**
     * Value for access flag {@code ACC_STRICT} for a {@code Method}.
     */
    public static final int ACC_STRICT = 0x0800;
    private String declaration;
    private String name;
    private String descriptor;

    MethodInfo() {
    }

    MethodInfo(final PosDataInputStream posDataInputStream, final AbstractCPInfo[] cp)
            throws IOException, ClassFormatException {
        this.startPos = posDataInputStream.getPos();
        this.length = -1;

        this.access_flags = posDataInputStream.readUnsignedShort();
        this.name_index = posDataInputStream.readUnsignedShort();
        this.descriptor_index = posDataInputStream.readUnsignedShort();

        this.attributes_count = posDataInputStream.readUnsignedShort();
        final int attrCount = this.attributes_count;
        if (attrCount > 0) {
            this.attributes = new AttributeInfo[attrCount];
            for (int i = 0; i < attrCount; i++) {
                this.attributes[i] = AttributeInfo.parse(posDataInputStream, cp);
                if(this.attributes[i].getType().equals("Code")){
                    this.code = (AttributeCode) this.attributes[i];
                }
            }
        }

        this.calculateLength();
    }

    private void calculateLength() {
        this.length = 8;

        for (int i = 0; i < this.attributes_count; i++) {
            this.length += this.attributes[i].getLength();
        }
    }
    
    public String getMethodDeclaration(){
        return this.name + this.descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
    
    
    
    

    ///////////////////////////////////////////////////////////////////////////
    // Get raw data
    /**
     * Get the value of {@code access_flags}.
     *
     * @return The value of {@code access_flags}
     */
    public int getAccessFlags() {
        return this.access_flags;
    }

    public byte [] getByteCode(){
        return code.getCode();
    }
    
    public int getMaxLocals(){
        return code.getMaxLocals();
    }

    /**
     * Get the value of {@code name_index}.
     *
     * @return The value of {@code name_index}
     */
    public int getNameIndex() {
        return this.name_index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of {@code descriptor_index}.
     *
     * @return The value of {@code descriptor_index}
     */
    public int getDescriptorIndex() {
        return this.descriptor_index;
    }

    /**
     * Get the value of {@code attributes_count}.
     *
     * @return The value of {@code attributes_count}
     */
    public int getAttributesCount() {
        return this.attributes_count;
    }

    /**
     * Get the value of {@code attributes}[{@code index}].
     *
     * @param index Index of the method attribute(s)
     * @return The value of {@code attributes}[{@code index}]
     */
    public AttributeInfo getAttribute(final int index) {
        AttributeInfo info = null;
        if (this.attributes != null) {
            info = this.attributes[index];
        }
        return info;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get extracted data
    /**
     * Generate the modifier of a {@code Method} from the {@code access_flags} value.
     *
     * @return A string for modifier
     */
    public String getModifiers() {
        final StringBuilder sb = new StringBuilder(20);
        Boolean isTheFirstModifier = true;

        if ((this.access_flags & MethodInfo.ACC_PUBLIC) > 0) {
            sb.append("public");
            isTheFirstModifier = false;
        } else if ((this.access_flags & MethodInfo.ACC_PRIVATE) > 0) {
            sb.append("private");
            isTheFirstModifier = false;
        } else if ((this.access_flags & MethodInfo.ACC_PROTECTED) > 0) {
            sb.append("protected");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_ABSTRACT) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("abstract");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_STATIC) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("static");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_FINAL) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("final");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_NATIVE) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("native");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_SYNCHRONIZED) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("synchronized");
            isTheFirstModifier = false;
        }

        if ((this.access_flags & MethodInfo.ACC_STRICT) > 0) {
            if (isTheFirstModifier == false) {
                sb.append(' ');
            }
            sb.append("strictfp");
            isTheFirstModifier = false;
        }

        return sb.toString();
    }

    final void setDeclaration(final String declaration) {
        this.declaration = declaration;
    }

    /**
     * Get the declaration of the field. The declaration is generated by
     * {@code access_flags}, {@code name_index} and {@code descriptor_index}.
     *
     * @return {@code Method} declaration
     */
    public String getDeclaration() {
        return this.declaration;
    }
}
