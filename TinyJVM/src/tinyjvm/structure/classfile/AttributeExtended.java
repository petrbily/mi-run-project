/*
 * AttributeExtended.java    12:37 AM, August 11, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * The class for the Extended attribute. Non-standard attribute, all of the
 * attributes which are not defined in the VM Spec will be represented by this
 * class.
 *
 * <pre>
 *    attribute_info {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u1 info[attribute_length];
 *    }
 * </pre>
 *
 * The {@code info} is the raw byte array data.
 *
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public class AttributeExtended extends AttributeInfo {

    transient private byte[] rawData;

    AttributeExtended(final int nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, ClassFormatException {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length > 0) {
            this.rawData = new byte[this.attribute_length];
            posDataInputStream.read(this.rawData);
        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code info}, in raw data format.
     *
     * @return The value of {@code info}
     */
    public byte[] getRawData() {
        return this.rawData;
    }
}
