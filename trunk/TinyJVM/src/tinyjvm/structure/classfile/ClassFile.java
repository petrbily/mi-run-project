/*
 * ClassFile.java    2:58 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package tinyjvm.structure.classfile;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tinyjvm.MyLogger;

/**
 * Represents a {@code class} file. A {@code class} file structure has the
 * following format:
 *
 * <pre>
 *    ClassFile {
 *        u4 magic;
 *        u2 minor_version;
 *        u2 major_version;
 *        u2 constant_pool_count;
 *        cp_info constant_pool[constant_pool_count-1];
 *        u2 access_flags;
 *        u2 this_class;
 *        u2 super_class;
 *        u2 interfaces_count;
 *        u2 interfaces[interfaces_count];
 *        u2 fields_count;
 *        field_info fields[fields_count];
 *        u2 methods_count;
 *        method_info methods[methods_count];
 *        u2 attributes_count;
 *        attribute_info attributes[attributes_count];
 *    }
 * </pre>
 *
 * The {@code ClassFile} object is constructed from the class byte array.
 *
 *
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a
 * href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#74353">
 * VM Spec: The ClassFile Structure </a>
 */
public class ClassFile {

    private byte[] classByteArray;
    private PosDataInputStream posDataInputStream;
    /**
     * Magic number of {@code class} file.
     */
    public static final int MAGIC = 0xCAFEBABE;
    private int magic;
    // Class file Version
    private int minor_version;
    private int major_version;
    // Constant pool
    private int constant_pool_count;
    private AbstractCPInfo[] constant_pool;
    // Class Declaration
    private AccessFlags access_flags;
    private int this_class;
    private int super_class;
    private int interfaces_count;
    private int[] interfaces;
    // Field
    private int fields_count;
    private FieldInfo[] fields;
    // Method
    private int methods_count;
    private MethodInfo[] methods;
    // Attribute
    private int attributes_count;
    private AttributeInfo[] attributes;

    /**
     * Creates a new instance of ClassFile from byte array.
     *
     * @param classByteArray Byte array of a class file
     * @throws java.io.IOException Error happened when reading the byte array
     * @throws org.freeinternals.classfile.core.ClassFormatException The input
     * parameter {@code classByteArray} is not a valid class
     */
    public ClassFile(final byte[] classByteArray)
            throws java.io.IOException, ClassFormatException {
        this.classByteArray = classByteArray.clone();
        final ClassFile.Parser parser = new Parser();
        parser.parse();
        this.analysisDeclarations();
    }

    private void analysisDeclarations()
            throws ClassFormatException {

        // Analysis field declarations
        if (this.fields_count > 0) {
            String type = null;
            for (FieldInfo field : fields) {
                try {
                    type = SignatureConvertor.signature2Type(this.getConstantUtf8Value(field.getDescriptorIndex()));
                } catch (SignatureException se) {
                    type = "[Unexpected signature type]: " + this.getConstantUtf8Value(field.getDescriptorIndex());
                    //System.err.println(se.toString());
                }
                field.setDeclaration(String.format("%s %s %s",
                        field.getModifiers(),
                        type,
                        this.getConstantUtf8Value(field.getNameIndex())));
            }
        }


        // Analysis method declarations
        if (this.methods_count > 0) {
            String mtdReturnType = null;
            String mtdParameters = null;
            String mtdDescriptor = null;
            for (MethodInfo method : methods) {
                try {
                    mtdDescriptor = this.getConstantUtf8Value(method.getDescriptorIndex());
                    mtdReturnType = SignatureConvertor.parseMethodReturnType(mtdDescriptor);
                } catch (SignatureException se) {
                    mtdReturnType = String.format("[Unexpected method return type: %s]", this.getConstantUtf8Value(method.getDescriptorIndex()));
                    //System.err.println(se.toString());
                }
                try {
                    mtdParameters = SignatureConvertor.parseMethodParameters(this.getConstantUtf8Value(method.getDescriptorIndex()));
                } catch (SignatureException se) {
                    mtdParameters = String.format("[Unexpected method parameters: %s]", this.getConstantUtf8Value(method.getDescriptorIndex()));
                    //System.err.println(se.toString());
                }

                method.setDeclaration(String.format("%s %s %s %s",
                        method.getModifiers(),
                        mtdReturnType,
                        this.getConstantUtf8Value(method.getNameIndex()),
                        mtdParameters));
                method.setDescriptor(mtdDescriptor);
                
                method.setName(this.getConstantUtf8Value(method.getNameIndex()));
            }
        }
    }
    
    //Hegladans method TODO - udelat jednu metodu z tehle tri po sobe
    public String getMethodName(int cpIndex){
        String retVal = null;
        if(cpIndex > this.constant_pool_count || cpIndex < 0) return null;
        AbstractCPInfo tmp = this.constant_pool[cpIndex];
        if(tmp.getTag() == 10){
            try{
                int NameAndTypeIndex = ((ConstantMethodrefInfo) tmp).getNameAndTypeIndex();
                ConstantNameAndTypeInfo natInfo = (ConstantNameAndTypeInfo) this.constant_pool[NameAndTypeIndex];            
                retVal = this.getConstantUtf8Value(natInfo.getNameIndex());
            }catch(ClassFormatException io){
                        io.printStackTrace();
            }
        }
        return retVal;
    }
    
    //Hegladans method
    public String getMethodDescription(int cpIndex){
        String retVal = null;
        if(cpIndex > this.constant_pool_count || cpIndex < 0) return null;
        AbstractCPInfo tmp = this.constant_pool[cpIndex];
        if(tmp.getTag() == 10){
            try{
                int NameAndTypeIndex = ((ConstantMethodrefInfo) tmp).getNameAndTypeIndex();
                ConstantNameAndTypeInfo natInfo = (ConstantNameAndTypeInfo) this.constant_pool[NameAndTypeIndex];            
                retVal = this.getConstantUtf8Value(natInfo.getDescriptorIndex());
            }catch(ClassFormatException io){
                        io.printStackTrace();
            }
        }
        return retVal;
    }
    
    //Hegladans method
    public String getMethodsClassName(int cpIndex){
        String retVal = null;
        if(cpIndex > this.constant_pool_count || cpIndex < 0) return null;
        AbstractCPInfo tmp = this.constant_pool[cpIndex];
        if(tmp.getTag() == 10){
            try{
                int classIndex = ((ConstantMethodrefInfo) tmp).getClassIndex();            
                retVal = this.getConstantUtf8Value(((ConstantClassInfo) this.constant_pool[classIndex]).getNameIndex());
            }catch(ClassFormatException io){
                        io.printStackTrace();
            }
        }
        return retVal;
    }
    
    //Hegladan's method
    public String getFieldName(int cpIndex) {
        String retVal = null;
        if(cpIndex > this.constant_pool_count || cpIndex < 0) return null;
        AbstractCPInfo tmp = this.constant_pool[cpIndex];
        if(tmp.getTag() == 9){
            try{
                int NameAndTypeIndex = ((ConstantFieldrefInfo) tmp).getNameAndTypeIndex();
                ConstantNameAndTypeInfo natInfo = (ConstantNameAndTypeInfo) this.constant_pool[NameAndTypeIndex];            
                retVal = this.getConstantUtf8Value(natInfo.getNameIndex());
            }catch(ClassFormatException io){
                        io.printStackTrace();
            }
        }
        return retVal;
    }
    
    //Hegladan's method
    public String getClassName(int cpIndex){
        String retVal = null;
        if(cpIndex > this.constant_pool_count || cpIndex < 0) return null;
        AbstractCPInfo tmp = this.constant_pool[cpIndex];
        if(tmp.getTag() == 7){
            try{
                int classNameIndex = ((ConstantClassInfo) tmp).getNameIndex();            
                retVal = this.getConstantUtf8Value(classNameIndex);
            }catch(ClassFormatException io){
                io.printStackTrace();
            }
        }
        return retVal;
    }
    
    //Hegladans method
    public int getCPTag(final int cpIndex){
        return this.constant_pool[cpIndex].getTag();
    }
    
    //Hegladans method
    public String getConstantStringValue(int cpIndex) {
        AbstractCPInfo cpInfo = this.constant_pool[cpIndex];
        if(cpInfo instanceof ConstantStringInfo){
            int index = ((ConstantStringInfo) cpInfo).getStringIndex();
            try {
                return getConstantUtf8Value(index);
            } catch (ClassFormatException ex) {
                MyLogger.logError(ex.getMessage());
            }
        }
        return null;
    }
    
    //Hegladans method
    public int getConstantIntegerValue(int cpIndex){
        AbstractCPInfo cpInfo = this.constant_pool[cpIndex];
        if(cpInfo instanceof ConstantIntegerInfo){
            return ((ConstantIntegerInfo) cpInfo).getValue();
        }else{   
            MyLogger.logInfo((String.format("Unexpected constant pool type: Integer(%d) expected, but it is '%d'.",AbstractCPInfo.CONSTANT_Integer,this.constant_pool[cpIndex].tag)));
        }
        return -1;
    }
        
    public String getConstantUtf8Value(final int cpIndex)
            throws ClassFormatException {
        String returnValue = null;

        if ((cpIndex == 0) || (cpIndex >= this.constant_pool_count)) {
            throw new ClassFormatException(String.format(
                    "Constant Pool index is out of range. CP index cannot be zero, and should be less than CP count (=%d). CP index = %d.",
                    this.constant_pool_count,
                    cpIndex));
        }

        if (this.constant_pool[cpIndex].tag == AbstractCPInfo.CONSTANT_Utf8) {
            final ConstantUtf8Info utf8Info = (ConstantUtf8Info) this.constant_pool[cpIndex];
            returnValue = utf8Info.getValue();
        } else {
            throw new ClassFormatException(String.format(
                    "Unexpected constant pool type: Utf8(%d) expected, but it is '%d'.",
                    AbstractCPInfo.CONSTANT_Utf8,
                    this.constant_pool[cpIndex].tag));
        }

        return returnValue;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get raw data
    /**
     * Get the byte array of current class.
     *
     * @return Byte array of the class
     */
    public byte[] getClassByteArray() {
        return this.classByteArray;
    }

    /**
     * Get part of the class byte array. The array begins at the specified
     * {@code startIndex} and extends to the byte at
     * {@code startIndex}+{@code length}.
     *
     * @param startIndex The start index
     * @param length The length of the array
     * @return Part of the class byte array
     */
    public byte[] getClassByteArray(final int startIndex, final int length) {
        if ((startIndex < 0) || (length < 1)) {
            throw new IllegalArgumentException("startIndex or length is not valid. startIndex = " + startIndex + ", length = " + length);
        }
        if (startIndex + length - 1 > this.classByteArray.length) {
            throw new ArrayIndexOutOfBoundsException("The last item index is bigger than class byte array size.");
        }

        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = this.classByteArray[startIndex + i];
        }

        return data;
    }

    /**
     * Get the length of the class byte array.
     *
     * @return Length of class byte array
     */
    public int getByteArraySize() {
        return this.classByteArray.length;
    }

    public int getMinorVersion() {
        return this.minor_version;
    }

    public int getMajorVersion() {
        return this.major_version;
    }

    /**
     * Get the {@code constant_pool_count} of the {@code ClassFile} structure.
     *
     * @return The {@code constant_pool_count}
     */
    public int getCPCount() {
        return this.constant_pool_count;
    }

    /**
     * Get the {@code constant_pool[]} of the {@code ClassFile} structure.
     *
     * @return The {@code constant_pool[]}
     */
    public AbstractCPInfo[] getConstantPool() {
        return this.constant_pool;
    }

    /**
     * Returns a string of the constant pool item at the specified
     * {@code index}.
     *
     * @param index Index in the constant pool
     * @return String of the constant pool item at {@code index}
     */
    public String getCPDescription(final int index) {
        // Invalid index
        if (index >= this.constant_pool_count) {
            return null;
        }

        // Special index: empty
        if (index == 0) {
            return null;
        }

        return new CPDescr().getCPDescr(index);
    }

    /**
     * Get the {@code access_flags} of the {@code ClassFile} structure.
     *
     * @return The {@code access_flags}
     */
    public AccessFlags getAccessFlags() {
        return this.access_flags;
    }

    /**
     * Get the {@code this_class} of the {@code ClassFile} structure.
     *
     * @return The {@code this_class}
     */
    public int getThisClass() {
        return this.this_class;
    }
    
    /**
     * Get the {@code this_class_name} of the {@code ClassFile} structure.
     *
     * @return The {@code this_class_name}
     */
    public String getThisClassName(){
        ConstantClassInfo conClass = (ConstantClassInfo)this.constant_pool[this_class];
        String className = null;
        try{
            className = this.getConstantUtf8Value(conClass.getNameIndex());
        }catch(ClassFormatException cfe){
            cfe.printStackTrace();
        }
        return className;
    }

    /**
     * Get the {@code super_class} of the {@code ClassFile} structure.
     *
     * @return The {@code super_class}
     */
    public int getSuperClass() {
        return this.super_class;
    }

    /**
     * Get the {@code interfaces_count} of the {@code ClassFile} structure.
     *
     * @return The {@code interfaces_count}
     */
    public int getInterfacesCount() {
        return this.interfaces_count;
    }

    /**
     * Get the {@code interfaces}[] of the {@code ClassFile} structure.
     *
     * @return The {@code interfaces}[]
     */
    public int[] getInterfaces() {
        return this.interfaces;
    }

    /**
     * Get the {@code fields_count} of the {@code ClassFile} structure.
     *
     * @return The {@code fields_count}
     */
    public int getFieldCount() {
        return this.fields_count;
    }

    /**
     * Get the {@code fields}[] of the {@code ClassFile} structure.
     *
     * @return The {@code fields}[]
     */
    public FieldInfo[] getFields() {
        return this.fields;
    }

    /**
     * Get the {@code methods_count} of the {@code ClassFile} structure.
     *
     * @return The {@code methods_count}
     */
    public int getMethodCount() {
        return this.methods_count;
    }
    
    /**
     * Get the {@code methods}[] of the {@code ClassFile} structure.
     *
     * @return The {@code methods}[]
     */
    public MethodInfo[] getMethods() {
        return this.methods;
    }

    /**
     * Get the {@code attributes_count} of the {@code ClassFile} structure.
     *
     * @return The {@code attributes_count}
     */
    public int getAttributeCount() {
        return this.attributes_count;
    }

    /**
     * Get the {@code attributes}[] of the {@code ClassFile} structure.
     *
     * @return The {@code attributes}[]
     */
    public AttributeInfo[] getAttributes() {
        return this.attributes;
    }
    
    public ArrayList<String> getClassNamesFromCP(){
        ArrayList<String> classNames = new ArrayList();
        String thisClassName = this.getThisClassName();
        for(AbstractCPInfo cpInf : this.getConstantPool()){
            if(cpInf == null) continue;
            if(cpInf.tag == 7){
                String className = null;
                try{
                    className = this.getConstantUtf8Value(((ConstantClassInfo)cpInf).getNameIndex());
                }catch(ClassFormatException cfe){
                    cfe.printStackTrace();
                }
                if(className == null || thisClassName.equals(className)) continue;
                classNames.add(className);
            }
        }
        return classNames;
    }

    
    public MethodInfo getMethod(String methodDeclaration) {
        for (MethodInfo mi : methods) {
            if(mi.getMethodDeclaration().equals(methodDeclaration)){
                return mi;
            }
        }        
        return null;
    }
    

    ///////////////////////////////////////////////////////////////////////////
    // Get extracted data
    ///////////////////////////////////////////////////////////////////////////
    // Internal Classes
    private class Parser {

        Parser() {
        }

        public void parse()
                throws java.io.IOException, ClassFormatException {
            final PosByteArrayInputStream posByteArrayInputStream = new PosByteArrayInputStream(classByteArray);
            ClassFile.this.posDataInputStream = new PosDataInputStream(posByteArrayInputStream);

            ClassFile.this.magic = ClassFile.this.posDataInputStream.readInt();
            if (ClassFile.this.magic != ClassFile.MAGIC) {
                throw new ClassFormatException("The magic number of the byte array is not 0xCAFEBABE");
            }

            this.parseClassFileVersion();
            this.parseConstantPool();
            this.parseClassDeclaration();
            this.parseFields();
            this.parseMethods();
            this.parseAttributes();
        }

        private void parseClassFileVersion()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.minor_version = posDataInputStream.readUnsignedShort();
            ClassFile.this.major_version = posDataInputStream.readUnsignedShort();
        }

        private void parseConstantPool()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.constant_pool_count = posDataInputStream.readUnsignedShort();;
            final int cp_count = ClassFile.this.constant_pool_count;

            ClassFile.this.constant_pool = new AbstractCPInfo[cp_count];
            short tag;
            for (int i = 1; i < cp_count; i++) {
                tag = (short) ClassFile.this.posDataInputStream.readUnsignedByte();

                switch (tag) {
                    case AbstractCPInfo.CONSTANT_Utf8:
                        ClassFile.this.constant_pool[i] = new ConstantUtf8Info(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_Integer:
                        ClassFile.this.constant_pool[i] = new ConstantIntegerInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_Float:
                        ClassFile.this.constant_pool[i] = new ConstantFloatInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_Long:
                        ClassFile.this.constant_pool[i] = new ConstantLongInfo(ClassFile.this.posDataInputStream);
                        i++;
                        break;

                    case AbstractCPInfo.CONSTANT_Double:
                        ClassFile.this.constant_pool[i] = new ConstantDoubleInfo(ClassFile.this.posDataInputStream);
                        i++;
                        break;

                    case AbstractCPInfo.CONSTANT_Class:
                        ClassFile.this.constant_pool[i] = new ConstantClassInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_String:
                        ClassFile.this.constant_pool[i] = new ConstantStringInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_Fieldref:
                        ClassFile.this.constant_pool[i] = new ConstantFieldrefInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_Methodref:
                        ClassFile.this.constant_pool[i] = new ConstantMethodrefInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                        ClassFile.this.constant_pool[i] = new ConstantInterfaceMethodrefInfo(ClassFile.this.posDataInputStream);
                        break;

                    case AbstractCPInfo.CONSTANT_NameAndType:
                        ClassFile.this.constant_pool[i] = new ConstantNameAndTypeInfo(ClassFile.this.posDataInputStream);
                        break;

                    default:
                        throw new ClassFormatException(
                                String.format("Unreconizable constant pool type found. Constant pool tag: [%d]; class file offset: [%d].", tag, ClassFile.this.posDataInputStream.getPos() - 1));
                }

                // -- Debug information.
//                if (ClassFile.this.constant_pool[i] != null)
//                {
//                    System.out.print(ClassFile.this.constant_pool[i].getDescription());
//                }
//                else
//                {
//                    System.out.print(ClassFile.this.constant_pool[i-1].getDescription());
//                }
//                System.out.println ();
            }
        }

        private void parseClassDeclaration()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.access_flags = new AccessFlags(posDataInputStream.readUnsignedShort());
            ClassFile.this.this_class = posDataInputStream.readUnsignedShort();
            ClassFile.this.super_class = posDataInputStream.readUnsignedShort();

            ClassFile.this.interfaces_count = posDataInputStream.readUnsignedShort();
            if (ClassFile.this.interfaces_count > 0) {
                ClassFile.this.interfaces = new int [ClassFile.this.interfaces_count];
                for (int i = 0; i < ClassFile.this.interfaces_count; i++) {
                    ClassFile.this.interfaces[i] = posDataInputStream.readUnsignedShort();
                }
            }
        }

        private void parseFields()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.fields_count = posDataInputStream.readUnsignedShort();
            final int fieldCount = ClassFile.this.fields_count;
            if (fieldCount > 0) {
                ClassFile.this.fields = new FieldInfo[fieldCount];
                for (int i = 0; i < fieldCount; i++) {
                    ClassFile.this.fields[i] = new FieldInfo(ClassFile.this.posDataInputStream, ClassFile.this.constant_pool);
                }
            }
        }

        private void parseMethods()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.methods_count = posDataInputStream.readUnsignedShort();
            final int methodCount = ClassFile.this.methods_count;

            if (methodCount > 0) {
                ClassFile.this.methods = new MethodInfo[methodCount];
                for (int i = 0; i < methodCount; i++) {
                    ClassFile.this.methods[i] = new MethodInfo(ClassFile.this.posDataInputStream, ClassFile.this.constant_pool);
                }
            }
        }

        private void parseAttributes()
                throws java.io.IOException, ClassFormatException {
            ClassFile.this.attributes_count = posDataInputStream.readUnsignedShort();
            final int attributeCount = ClassFile.this.attributes_count;
            if (attributeCount > 0) {
                ClassFile.this.attributes = new AttributeInfo[attributeCount];
                for (int i = 0; i < attributeCount; i++) {
                    ClassFile.this.attributes[i] = AttributeInfo.parse(ClassFile.this.posDataInputStream, ClassFile.this.constant_pool);
                }
            }
        }
    }

    private static enum Descr_NameAndType {

        RAW(1), FIELD(2), METHOD(3);
        private final int enum_value;

        Descr_NameAndType(final int value) {
            this.enum_value = value;
        }

        public int value() {
            return this.enum_value;
        }
    }

    private class CPDescr {

        CPDescr() {
        }

        public String getCPDescr(final int index) {
            final StringBuilder sb = new StringBuilder(40);

            switch (ClassFile.this.constant_pool[index].getTag()) {
                case AbstractCPInfo.CONSTANT_Utf8:
                    sb.append("Utf8: ");
                    sb.append(this.getDescr_Utf8((ConstantUtf8Info) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Integer:
                    sb.append("Integer: ");
                    sb.append(this.getDescr_Integer((ConstantIntegerInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Float:
                    sb.append("Float: ");
                    sb.append(this.getDescr_Float((ConstantFloatInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Long:
                    sb.append("Long: ");
                    sb.append(this.getDescr_Long((ConstantLongInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Double:
                    sb.append("Double: ");
                    sb.append(this.getDescr_Double((ConstantDoubleInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Class:
                    sb.append("Class: ");
                    sb.append(this.getDescr_Class((ConstantClassInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_String:
                    sb.append("String: ");
                    sb.append(this.getDescr_String((ConstantStringInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Fieldref:
                    sb.append("Fieldref: ");
                    sb.append(this.getDescr_Fieldref((ConstantFieldrefInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_Methodref:
                    sb.append("Methodref: ");
                    sb.append(this.getDescr_Methodref((ConstantMethodrefInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                    sb.append("InterfaceMethodref: ");
                    sb.append(this.getDescr_InterfaceMethodref((ConstantInterfaceMethodrefInfo) ClassFile.this.constant_pool[index]));
                    break;
                case AbstractCPInfo.CONSTANT_NameAndType:
                    sb.append("NameAndType: ");
                    sb.append(this.getDescr_NameAndType(
                            (ConstantNameAndTypeInfo) ClassFile.this.constant_pool[index],
                            ClassFile.Descr_NameAndType.RAW));
                    break;
                default:
                    sb.append("!!! Un-supported CP type.");
                    break;
            }

            return sb.toString();
        }

        private String getDescr_Utf8(final ConstantUtf8Info info) {
            return info.getValue();
        }

        private String getDescr_Integer(final ConstantIntegerInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Float(final ConstantFloatInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Long(final ConstantLongInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Double(final ConstantDoubleInfo info) {
            return String.valueOf(info.getValue());
        }

        private String getDescr_Class(final ConstantClassInfo info) {
            // The value of the name_index item must be a valid index into the constant_pool table. 
            // The constant_pool entry at that index must be a CONSTANT_Utf8_info structure 
            // representing a valid fully qualified class or interface name encoded in internal form.
            return SignatureConvertor.parseClassSignature(this.getDescr_Utf8(
                    (ConstantUtf8Info) ClassFile.this.constant_pool[info.getNameIndex()]));
        }

        private String getDescr_String(final ConstantStringInfo info) {
            // The value of the string_index item must be a valid index into the constant_pool table. 
            // The constant_pool entry at that index must be a CONSTANT_Utf8_info (.4.7) structure 
            // representing the sequence of characters to which the String object is to be initialized.
            return SignatureConvertor.parseClassSignature(this.getDescr_Utf8(
                    (ConstantUtf8Info) ClassFile.this.constant_pool[info.getStringIndex()]));
        }

        private String getDescr_Fieldref(final ConstantFieldrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassFile.Descr_NameAndType.FIELD);
        }

        private String getDescr_Methodref(final ConstantMethodrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassFile.Descr_NameAndType.METHOD);
        }

        private String getDescr_InterfaceMethodref(final ConstantInterfaceMethodrefInfo info) {
            return this.getDescr_ref(
                    info.getClassIndex(),
                    info.getNameAndTypeIndex(),
                    ClassFile.Descr_NameAndType.METHOD);
        }

        private String getDescr_ref(final int classindex, final int natindex, final ClassFile.Descr_NameAndType type) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.getDescr_Class((ConstantClassInfo) ClassFile.this.constant_pool[classindex]));
            sb.append(".");
            sb.append(this.getDescr_NameAndType((ConstantNameAndTypeInfo) ClassFile.this.constant_pool[natindex], type));

            return sb.toString();
        }

        private String getDescr_NameAndType(final ConstantNameAndTypeInfo info, final ClassFile.Descr_NameAndType format) {
            final StringBuilder sb = new StringBuilder();
            String type;

            sb.append(this.getDescr_Utf8((ConstantUtf8Info) ClassFile.this.constant_pool[info.getNameIndex()]));
            sb.append(", ");
            type = this.getDescr_Utf8((ConstantUtf8Info) ClassFile.this.constant_pool[info.getDescriptorIndex()]);

            switch (format) {
                case RAW:
                    sb.append(type);
                    break;

                case FIELD:
                    try {
                        sb.append("type = ");
                        sb.append(SignatureConvertor.signature2Type(type));
                    } catch (SignatureException ex) {
                        Logger.getLogger(ClassFile.class.getName()).log(Level.SEVERE, null, ex);

                        sb.append(type);
                        sb.append(" !!! Un-recognized type");
                    }
                    break;

                case METHOD:
                    final StringBuilder sb_mtd = new StringBuilder();
                    try {
                        sb_mtd.append("parameter = ");
                        sb_mtd.append(SignatureConvertor.parseMethodParameters(type));
                        sb_mtd.append(", returns = ");
                        sb_mtd.append(SignatureConvertor.parseMethodReturnType(type));

                        sb.append(sb_mtd);
                    } catch (SignatureException ex) {
                        Logger.getLogger(ClassFile.class.getName()).log(Level.SEVERE, null, ex);

                        sb.append(type);
                        sb.append(" !!! Un-recognized type");
                    }
                    break;
                default:
                    break;
            }

            return sb.toString();
        }
    }
}
