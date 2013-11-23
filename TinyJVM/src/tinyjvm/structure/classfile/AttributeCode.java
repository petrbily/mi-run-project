/*
 * AttributeCode.java    5:09 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * The class for the {@code Code} attribute.
 * The {@code Code} attribute has the following format:
 *
 * <pre>
 *    Code_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 max_stack;
 *        u2 max_locals;
 *        u4 code_length;
 *        u1 code[code_length];
 *        u2 exception_table_length;
 *        {
 *                u2 start_pc;
 *                u2 end_pc;
 *                u2 handler_pc;
 *                u2 catch_type;
 *        } exception_table[exception_table_length];
 *        u2 attributes_count;
 *        attribute_info attributes[attributes_count];
 *    }
 * </pre>
 *
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1546">
 * VM Spec: The Code Attribute
 * </a>
 */
public class AttributeCode extends AttributeInfo {

    private transient final int max_stack;
    private transient final int max_locals;
    private transient final int code_length;
    private transient final byte[] code;
    private transient final int exception_table_length;
    private transient ExceptionTable[] exceptionTable;
    private transient final int attributes_count;
    private transient AttributeInfo[] attributes;

    AttributeCode(final int nameIndex, final String type, final PosDataInputStream posDataInputStream, final AbstractCPInfo[] cp)
            throws IOException, ClassFormatException {
        super(nameIndex, type, posDataInputStream);

        int i;

        this.max_stack = posDataInputStream.readUnsignedShort();
        this.max_locals = posDataInputStream.readUnsignedShort();
        this.code_length = posDataInputStream.readInt();
        this.code = new byte[this.code_length];
        posDataInputStream.read(this.code);

        this.exception_table_length = posDataInputStream.readUnsignedShort();
        if (this.exception_table_length > 0) {
            this.exceptionTable = new ExceptionTable[this.exception_table_length];
            for (i = 0; i < this.exception_table_length; i++) {
                this.exceptionTable[i] = new ExceptionTable(posDataInputStream);
            }
        }

        this.attributes_count = posDataInputStream.readUnsignedShort();
        final int attrCount = this.attributes_count;
        if (attrCount > 0) {
            this.attributes = new AttributeInfo[attrCount];
            for (i = 0; i < attrCount; i++) {
                this.attributes[i] = AttributeInfo.parse(posDataInputStream, cp);
            }

        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code max_stack}.
     *
     * @return The value of {@code max_stack}
     */
    public int getMaxStack() {
        return this.max_stack;
    }

    /**
     * Get the value of {@code max_locals}.
     *
     * @return The value of {@code max_locals}
     */
    public int getMaxLocals() {
        return this.max_locals;
    }

    /**
     * Get the value of {@code code_length}.
     *
     * @return The value of {@code code_length}
     */
    public int getCodeLength() {
        return this.code_length;
    }

    /**
     * Get the value of {@code code}.
     *
     * @return The value of {@code code}
     */
    public byte[] getCode() {
        return this.code;
    }

    /**
     * Get the value of {@code exception_table_length}.
     *
     * @return The value of {@code exception_table_length}
     */
    public int getExceptionTableLength() {
        return this.exception_table_length;
    }

    /**
     * Get the value of {@code exception_table}[{@code index}].
     *
     * @param index Index of the exception table
     * @return The value of {@code exception_table}[{@code index}]
     */
    public ExceptionTable getExceptionTable(final int index) {
        ExceptionTable et = null;
        if (this.exceptionTable != null) {
            et = this.exceptionTable[index];
        }
        return et;
    }

    /**
     * Get the value of {@code attributes_count}.
     *
     * @return The value of {@code attributes_count}
     */
    public int getAttributeCount() {
        return this.attributes_count;
    }

    /**
     * Get the value of {@code attributes}[{@code index}].
     *
     * @param index Zero-based index of the attributes
     * @return The value of {@code attributes}[{@code index}]
     */
    public AttributeInfo getAttribute(final int index) {
        return this.attributes[index];
    }

    /**
     * The {@code exception_table} structure in {@code Code} attribute.
     *
     * @author Amos Shi
     * @since JDK 6.0
     */
    public final class ExceptionTable extends ClassComponent {

        private transient final int start_pc;
        private transient final int end_pc;
        private transient final int handler_pc;
        private transient final int catch_type;

        private ExceptionTable(final PosDataInputStream posDataInputStream)
                throws IOException {
            this.startPos = posDataInputStream.getPos();
            this.length = 8;

            this.start_pc = posDataInputStream.readUnsignedShort();
            this.end_pc = posDataInputStream.readUnsignedShort();
            this.handler_pc = posDataInputStream.readUnsignedShort();
            this.catch_type = posDataInputStream.readUnsignedShort();
        }

        /**
         * Get the value of {@code start_pc}.
         *
         * @return The value of {@code start_pc}
         */
        public int getStartPc() {
            return this.start_pc;
        }

        /**
         * Get the value of {@code end_pc}.
         *
         * @return The value of {@code end_pc}
         */
        public int getEndPc() {
            return this.end_pc;
        }

        /**
         * Get the value of {@code handler_pc}.
         *
         * @return The value of {@code handler_pc}
         */
        public int getHandlerPc() {
            return this.handler_pc;
        }

        /**
         * Get the value of {@code catch_type}.
         *
         * @return The value of {@code catch_type}
         */
        public int getCatchType() {
            return this.catch_type;
        }
    }
}
