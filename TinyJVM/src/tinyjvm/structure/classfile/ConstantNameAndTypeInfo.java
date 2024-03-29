/*
 * ConstantNameAndTypeInfo.java    4:46 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * The class for the {@code CONSTANT_NameAndType_info} structure in constant pool.
 * The {@code CONSTANT_NameAndType_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_NameAndType_info {
 *        u1 tag;
 *        u2 name_index;
 *        u2 descriptor_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1327">
 * VM Spec: The CONSTANT_NameAndType_info Structure
 * </a>
 */
public class ConstantNameAndTypeInfo extends AbstractCPInfo {

    private final int name_index;
    private final int descriptor_index;

    ConstantNameAndTypeInfo(final PosDataInputStream posDataInputStream)
            throws IOException {
        super();
        this.tag = AbstractCPInfo.CONSTANT_NameAndType;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 5;

        this.name_index = posDataInputStream.readUnsignedShort();
        this.descriptor_index = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "NameAndType";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantNameAndTypeInfo: Start Position: [%d], length: [%d], value: name_index=[%d], descriptor_index=[%d].", this.startPos, this.length, this.name_index, this.descriptor_index);
    }

    /**
     * Get the value of {@code name_index}.
     *
     * @return The value of {@code name_index}
     */
    public int getNameIndex() {
        return this.name_index;
    }

    /**
     * Get the value of {@code descriptor_index}.
     *
     * @return The value of {@code descriptor_index}
     */
    public int getDescriptorIndex() {
        return this.descriptor_index;
    }
}
