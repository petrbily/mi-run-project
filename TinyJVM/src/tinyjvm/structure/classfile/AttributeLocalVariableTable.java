/*
 * AttributeLocalVariableTable.java    5:33 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.io.IOException;

/**
 * The class for the {@code LocalVariableTable} attribute.
 * The {@code LocalVariableTable} attribute has the following format:
 *
 * <pre>
 *    LocalVariableTable_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 local_variable_table_length;
 *        {   u2 start_pc;
 *            u2 length;
 *            u2 name_index;
 *            u2 descriptor_index;
 *            u2 index;
 *        } local_variable_table[local_variable_table_length];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#5956">
 * VM Spec: The LocalVariableTable Attribute
 * </a>
 */
public class AttributeLocalVariableTable extends AttributeInfo {

    private transient final int local_variable_table_length;
    private transient LocalVariableTable[] localVariableTable;

    AttributeLocalVariableTable(final int nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, ClassFormatException {
        super(nameIndex, type, posDataInputStream);

        this.local_variable_table_length = posDataInputStream.readUnsignedShort();
        if (this.local_variable_table_length > 0) {
            this.localVariableTable = new LocalVariableTable[this.local_variable_table_length];
            for (int i = 0; i < this.local_variable_table_length; i++) {
                this.localVariableTable[i] = new LocalVariableTable(posDataInputStream);
            }
        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code local_variable_table_length}.
     *
     * @return The value of {@code local_variable_table_length}
     */
    public int getLocalVariableTalbeLength() {
        return this.local_variable_table_length;
    }

    /**
     * Get the value of {@code local_variable_table}[{@code index}].
     *
     * @param index Index of the local variable table
     * @return The value of {@code local_variable_table}[{@code index}]
     */
    public LocalVariableTable getLocalVariableTable(final int index) {
        LocalVariableTable lvt = null;
        if (this.localVariableTable != null) {
            lvt = this.localVariableTable[index];
        }

        return lvt;
    }

    /**
     * The {@code local_variable_table} structure in {@code LocalVariableTable} attribute.
     *
     * @author Amos Shi
     * @since JDK 6.0
     * @see AttributeLocalVariableTable
     */
    public final class LocalVariableTable extends ClassComponent {

        private transient final int start_pc;
        private transient final int length_lvt;
        private transient final int name_index;
        private transient final int descriptor_index;
        private transient final int index;

        private LocalVariableTable(final PosDataInputStream posDataInputStream)
                throws IOException {
            this.startPos = posDataInputStream.getPos();
            super.length = 10;

            this.start_pc = posDataInputStream.readUnsignedShort();
            this.length_lvt = posDataInputStream.readUnsignedShort();
            this.name_index = posDataInputStream.readUnsignedShort();
            this.descriptor_index = posDataInputStream.readUnsignedShort();
            this.index = posDataInputStream.readUnsignedShort();
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
         * Get the value of code {@code length}.
         *
         * @return The value of code {@code length}
         */
        public int getCodeLength() {
            return this.length_lvt;
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

        /**
         * Get the value of {@code index}.
         *
         * @return The value of {@code index}
         */
        public int getIndex() {
            return this.index;
        }
    }
}
