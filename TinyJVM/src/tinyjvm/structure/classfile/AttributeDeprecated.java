/*
 * AttributeDeprecated.java    5:37 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * The class for the {@code Deprecated} attribute.
 * The {@code Deprecated} attribute has the following format:
 *
 * <pre>
 *    Deprecated_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#78232">
 * VM Spec: The Deprecated Attribute
 * </a>
 */
public class AttributeDeprecated extends AttributeInfo {

    AttributeDeprecated(final int nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, ClassFormatException {
        super(nameIndex, type, posDataInputStream);

        if (this.attribute_length != 0) {
            throw new ClassFormatException(String.format("The attribute_length of AttributeDeprecated is not 0, it is %d.", this.attribute_length));
        }

        super.checkSize(posDataInputStream.getPos());
    }
}
