/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tinyjvm.execution;

/**
 *
 * @author Daniel
 */
public final class opcodeValue {
    
    
    public static final String FORMAT_SD = "%s %d";
    public static final String FORMAT_SDS = "%s %d [%s]";
    // Opcode value
    public static final int op_nop = 0;
    public static final int op_aconst_null = 1;
    public static final int op_iconst_m1 = 2;
    public static final int op_iconst_0 = 3;
    public static final int op_iconst_1 = 4;
    public static final int op_iconst_2 = 5;
    public static final int op_iconst_3 = 6;
    public static final int op_iconst_4 = 7;
    public static final int op_iconst_5 = 8;
    public static final int op_lconst_0 = 9;
    public static final int op_lconst_1 = 10;
    public static final int op_fconst_0 = 11;
    public static final int op_fconst_1 = 12;
    public static final int op_fconst_2 = 13;
    public static final int op_dconst_0 = 14;
    public static final int op_dconst_1 = 15;
    public static final int op_bipush = 16;
    public static final int op_sipush = 17;
    public static final int op_ldc = 18;
    public static final int op_ldc_w = 19;
    public static final int op_ldc2_w = 20;
    public static final int op_iload = 21;
    public static final int op_lload = 22;
    public static final int op_fload = 23;
    public static final int op_dload = 24;
    public static final int op_aload = 25;
    public static final int op_iload_0 = 26;
    public static final int op_iload_1 = 27;
    public static final int op_iload_2 = 28;
    public static final int op_iload_3 = 29;
    public static final int op_lload_0 = 30;
    public static final int op_lload_1 = 31;
    public static final int op_lload_2 = 32;
    public static final int op_lload_3 = 33;
    public static final int op_fload_0 = 34;
    public static final int op_fload_1 = 35;
    public static final int op_fload_2 = 36;
    public static final int op_fload_3 = 37;
    public static final int op_dload_0 = 38;
    public static final int op_dload_1 = 39;
    public static final int op_dload_2 = 40;
    public static final int op_dload_3 = 41;
    public static final int op_aload_0 = 42;
    public static final int op_aload_1 = 43;
    public static final int op_aload_2 = 44;
    public static final int op_aload_3 = 45;
    public static final int op_iaload = 46;
    public static final int op_laload = 47;
    public static final int op_faload = 48;
    public static final int op_daload = 49;
    public static final int op_aaload = 50;
    public static final int op_baload = 51;
    public static final int op_caload = 52;
    public static final int op_saload = 53;
    public static final int op_istore = 54;
    public static final int op_lstore = 55;
    public static final int op_fstore = 56;
    public static final int op_dstore = 57;
    public static final int op_astore = 58;
    public static final int op_istore_0 = 59;
    public static final int op_istore_1 = 60;
    public static final int op_istore_2 = 61;
    public static final int op_istore_3 = 62;
    public static final int op_lstore_0 = 63;
    public static final int op_lstore_1 = 64;
    public static final int op_lstore_2 = 65;
    public static final int op_lstore_3 = 66;
    public static final int op_fstore_0 = 67;
    public static final int op_fstore_1 = 68;
    public static final int op_fstore_2 = 69;
    public static final int op_fstore_3 = 70;
    public static final int op_dstore_0 = 71;
    public static final int op_dstore_1 = 72;
    public static final int op_dstore_2 = 73;
    public static final int op_dstore_3 = 74;
    public static final int op_astore_0 = 75;
    public static final int op_astore_1 = 76;
    public static final int op_astore_2 = 77;
    public static final int op_astore_3 = 78;
    public static final int op_iastore = 79;
    public static final int op_lastore = 80;
    public static final int op_fastore = 81;
    public static final int op_dastore = 82;
    public static final int op_aastore = 83;
    public static final int op_bastore = 84;
    public static final int op_castore = 85;
    public static final int op_sastore = 86;
    public static final int op_pop = 87;
    public static final int op_pop2 = 88;
    public static final int op_dup = 89;
    public static final int op_dup_x1 = 90;
    public static final int op_dup_x2 = 91;
    public static final int op_dup2 = 92;
    public static final int op_dup2_x1 = 93;
    public static final int op_dup2_x2 = 94;
    public static final int op_swap = 95;
    public static final int op_iadd = 96;
    public static final int op_ladd = 97;
    public static final int op_fadd = 98;
    public static final int op_dadd = 99;
    public static final int op_isub = 100;
    public static final int op_lsub = 101;
    public static final int op_fsub = 102;
    public static final int op_dsub = 103;
    public static final int op_imul = 104;
    public static final int op_lmul = 105;
    public static final int op_fmul = 106;
    public static final int op_dmul = 107;
    public static final int op_idiv = 108;
    public static final int op_ldiv = 109;
    public static final int op_fdiv = 110;
    public static final int op_ddiv = 111;
    public static final int op_irem = 112;
    public static final int op_lrem = 113;
    public static final int op_frem = 114;
    public static final int op_drem = 115;
    public static final int op_ineg = 116;
    public static final int op_lneg = 117;
    public static final int op_fneg = 118;
    public static final int op_dneg = 119;
    public static final int op_ishl = 120;
    public static final int op_lshl = 121;
    public static final int op_ishr = 122;
    public static final int op_lshr = 123;
    public static final int op_iushr = 124;
    public static final int op_lushr = 125;
    public static final int op_iand = 126;
    public static final int op_land = 127;
    public static final int op_ior = 128;
    public static final int op_lor = 129;
    public static final int op_ixor = 130;
    public static final int op_lxor = 131;
    public static final int op_iinc = 132;
    public static final int op_i2l = 133;
    public static final int op_i2f = 134;
    public static final int op_i2d = 135;
    public static final int op_l2i = 136;
    public static final int op_l2f = 137;
    public static final int op_l2d = 138;
    public static final int op_f2i = 139;
    public static final int op_f2l = 140;
    public static final int op_f2d = 141;
    public static final int op_d2i = 142;
    public static final int op_d2l = 143;
    public static final int op_d2f = 144;
    public static final int op_i2b = 145;
    public static final int op_i2c = 146;
    public static final int op_i2s = 147;
    public static final int op_lcmp = 148;
    public static final int op_fcmpl = 149;
    public static final int op_fcmpg = 150;
    public static final int op_dcmpl = 151;
    public static final int op_dcmpg = 152;
    public static final int op_ifeq = 153;
    public static final int op_ifne = 154;
    public static final int op_iflt = 155;
    public static final int op_ifge = 156;
    public static final int op_ifgt = 157;
    public static final int op_ifle = 158;
    public static final int op_if_icmpeq = 159;
    public static final int op_if_icmpne = 160;
    public static final int op_if_icmplt = 161;
    public static final int op_if_icmpge = 162;
    public static final int op_if_icmpgt = 163;
    public static final int op_if_icmple = 164;
    public static final int op_if_acmpeq = 165;
    public static final int op_if_acmpne = 166;
    public static final int op_goto = 167;
    public static final int op_jsr = 168;
    public static final int op_ret = 169;
    public static final int op_tableswitch = 170;
    public static final int op_lookupswitch = 171;
    public static final int op_ireturn = 172;
    public static final int op_lreturn = 173;
    public static final int op_freturn = 174;
    public static final int op_dreturn = 175;
    public static final int op_areturn = 176;
    public static final int op_return = 177;
    public static final int op_getstatic = 178;
    public static final int op_putstatic = 179;
    public static final int op_getfield = 180;
    public static final int op_putfield = 181;
    public static final int op_invokevirtual = 182;
    public static final int op_invokespecial = 183;
    public static final int op_invokestatic = 184;
    public static final int op_invokeinterface = 185;
    // 186: For historical reasons, opcode value 186 is not used.
    public static final int op_new = 187;
    public static final int op_newarray = 188;
    public static final int op_anewarray = 189;
    public static final int op_arraylength = 190;
    public static final int op_athrow = 191;
    public static final int op_checkcast = 192;
    public static final int op_instanceof = 193;
    public static final int op_monitorenter = 194;
    public static final int op_monitorexit = 195;
    public static final int op_wide = 196;
    public static final int op_multianewarray = 197;
    public static final int op_ifnull = 198;
    public static final int op_ifnonnull = 199;
    public static final int op_goto_w = 200;
    public static final int op_jsr_w = 201;
    // Opcode name text
    public static final String name_nop = "nop";
    public static final String name_aconst_null = "aconst_null";
    public static final String name_iconst_m1 = "iconst_m1";
    public static final String name_iconst_0 = "iconst_0";
    public static final String name_iconst_1 = "iconst_1";
    public static final String name_iconst_2 = "iconst_2";
    public static final String name_iconst_3 = "iconst_3";
    public static final String name_iconst_4 = "iconst_4";
    public static final String name_iconst_5 = "iconst_5";
    public static final String name_lconst_0 = "lconst_0";
    public static final String name_lconst_1 = "lconst_1";
    public static final String name_fconst_0 = "fconst_0";
    public static final String name_fconst_1 = "fconst_1";
    public static final String name_fconst_2 = "fconst_2";
    public static final String name_dconst_0 = "dconst_0";
    public static final String name_dconst_1 = "dconst_1";
    public static final String name_bipush = "bipush";
    public static final String name_sipush = "sipush";
    public static final String name_ldc = "ldc";
    public static final String name_ldc_w = "ldc_w";
    public static final String name_ldc2_w = "ldc2_w";
    public static final String name_iload = "iload";
    public static final String name_lload = "lload";
    public static final String name_fload = "fload";
    public static final String name_dload = "dload";
    public static final String name_aload = "aload";
    public static final String name_iload_0 = "iload_0";
    public static final String name_iload_1 = "iload_1";
    public static final String name_iload_2 = "iload_2";
    public static final String name_iload_3 = "iload_3";
    public static final String name_lload_0 = "lload_0";
    public static final String name_lload_1 = "lload_1";
    public static final String name_lload_2 = "lload_2";
    public static final String name_lload_3 = "lload_3";
    public static final String name_fload_0 = "fload_0";
    public static final String name_fload_1 = "fload_1";
    public static final String name_fload_2 = "fload_2";
    public static final String name_fload_3 = "fload_3";
    public static final String name_dload_0 = "dload_0";
    public static final String name_dload_1 = "dload_1";
    public static final String name_dload_2 = "dload_2";
    public static final String name_dload_3 = "dload_3";
    public static final String name_aload_0 = "aload_0";
    public static final String name_aload_1 = "aload_1";
    public static final String name_aload_2 = "aload_2";
    public static final String name_aload_3 = "aload_3";
    public static final String name_iaload = "iaload";
    public static final String name_laload = "laload";
    public static final String name_faload = "faload";
    public static final String name_daload = "daload";
    public static final String name_aaload = "aaload";
    public static final String name_baload = "baload";
    public static final String name_caload = "caload";
    public static final String name_saload = "saload";
    public static final String name_istore = "istore";
    public static final String name_lstore = "lstore";
    public static final String name_fstore = "fstore";
    public static final String name_dstore = "dstore";
    public static final String name_astore = "astore";
    public static final String name_istore_0 = "istore_0";
    public static final String name_istore_1 = "istore_1";
    public static final String name_istore_2 = "istore_2";
    public static final String name_istore_3 = "istore_3";
    public static final String name_lstore_0 = "lstore_0";
    public static final String name_lstore_1 = "lstore_1";
    public static final String name_lstore_2 = "lstore_2";
    public static final String name_lstore_3 = "lstore_3";
    public static final String name_fstore_0 = "fstore_0";
    public static final String name_fstore_1 = "fstore_1";
    public static final String name_fstore_2 = "fstore_2";
    public static final String name_fstore_3 = "fstore_3";
    public static final String name_dstore_0 = "dstore_0";
    public static final String name_dstore_1 = "dstore_1";
    public static final String name_dstore_2 = "dstore_2";
    public static final String name_dstore_3 = "dstore_3";
    public static final String name_astore_0 = "astore_0";
    public static final String name_astore_1 = "astore_1";
    public static final String name_astore_2 = "astore_2";
    public static final String name_astore_3 = "astore_3";
    public static final String name_iastore = "iastore";
    public static final String name_lastore = "lastore";
    public static final String name_fastore = "fastore";
    public static final String name_dastore = "dastore";
    public static final String name_aastore = "aastore";
    public static final String name_bastore = "bastore";
    public static final String name_castore = "castore";
    public static final String name_sastore = "sastore";
    public static final String name_pop = "pop";
    public static final String name_pop2 = "pop2";
    public static final String name_dup = "dup";
    public static final String name_dup_x1 = "dup_x1";
    public static final String name_dup_x2 = "dup_x2";
    public static final String name_dup2 = "dup2";
    public static final String name_dup2_x1 = "dup2_x1";
    public static final String name_dup2_x2 = "dup2_x2";
    public static final String name_swap = "swap";
    public static final String name_iadd = "iadd";
    public static final String name_ladd = "ladd";
    public static final String name_fadd = "fadd";
    public static final String name_dadd = "dadd";
    public static final String name_isub = "isub";
    public static final String name_lsub = "lsub";
    public static final String name_fsub = "fsub";
    public static final String name_dsub = "dsub";
    public static final String name_imul = "imul";
    public static final String name_lmul = "lmul";
    public static final String name_fmul = "fmul";
    public static final String name_dmul = "dmul";
    public static final String name_idiv = "idiv";
    public static final String name_ldiv = "ldiv";
    public static final String name_fdiv = "fdiv";
    public static final String name_ddiv = "ddiv";
    public static final String name_irem = "irem";
    public static final String name_lrem = "lrem";
    public static final String name_frem = "frem";
    public static final String name_drem = "drem";
    public static final String name_ineg = "ineg";
    public static final String name_lneg = "lneg";
    public static final String name_fneg = "fneg";
    public static final String name_dneg = "dneg";
    public static final String name_ishl = "ishl";
    public static final String name_lshl = "lshl";
    public static final String name_ishr = "ishr";
    public static final String name_lshr = "lshr";
    public static final String name_iushr = "iushr";
    public static final String name_lushr = "lushr";
    public static final String name_iand = "iand";
    public static final String name_land = "land";
    public static final String name_ior = "ior";
    public static final String name_lor = "lor";
    public static final String name_ixor = "ixor";
    public static final String name_lxor = "lxor";
    public static final String name_iinc = "iinc";
    public static final String name_i2l = "i2l";
    public static final String name_i2f = "i2f";
    public static final String name_i2d = "i2d";
    public static final String name_l2i = "l2i";
    public static final String name_l2f = "l2f";
    public static final String name_l2d = "l2d";
    public static final String name_f2i = "f2i";
    public static final String name_f2l = "f2l";
    public static final String name_f2d = "f2d";
    public static final String name_d2i = "d2i";
    public static final String name_d2l = "d2l";
    public static final String name_d2f = "d2f";
    public static final String name_i2b = "i2b";
    public static final String name_i2c = "i2c";
    public static final String name_i2s = "i2s";
    public static final String name_lcmp = "lcmp";
    public static final String name_fcmpl = "fcmpl";
    public static final String name_fcmpg = "fcmpg";
    public static final String name_dcmpl = "dcmpl";
    public static final String name_dcmpg = "dcmpg";
    public static final String name_ifeq = "ifeq";
    public static final String name_ifne = "ifne";
    public static final String name_iflt = "iflt";
    public static final String name_ifge = "ifge";
    public static final String name_ifgt = "ifgt";
    public static final String name_ifle = "ifle";
    public static final String name_if_icmpeq = "if_icmpeq";
    public static final String name_if_icmpne = "if_icmpne";
    public static final String name_if_icmplt = "if_icmplt";
    public static final String name_if_icmpge = "if_icmpge";
    public static final String name_if_icmpgt = "if_icmpgt";
    public static final String name_if_icmple = "if_icmple";
    public static final String name_if_acmpeq = "if_acmpeq";
    public static final String name_if_acmpne = "if_acmpne";
    public static final String name_goto = "goto";
    public static final String name_jsr = "jsr";
    public static final String name_ret = "ret";
    public static final String name_tableswitch = "tableswitch";
    public static final String name_lookupswitch = "lookupswitch";
    public static final String name_ireturn = "ireturn";
    public static final String name_lreturn = "lreturn";
    public static final String name_freturn = "freturn";
    public static final String name_dreturn = "dreturn";
    public static final String name_areturn = "areturn";
    public static final String name_return = "return";
    public static final String name_getstatic = "getstatic";
    public static final String name_putstatic = "putstatic";
    public static final String name_getfield = "getfield";
    public static final String name_putfield = "putfield";
    public static final String name_invokevirtual = "invokevirtual";
    public static final String name_invokespecial = "invokespecial";
    public static final String name_invokestatic = "invokestatic";
    public static final String name_invokeinterface = "invokeinterface";
    public static final String name_new = "name_new";
    public static final String name_newarray = "newarray";
    public static final String name_anewarray = "anewarray";
    public static final String name_arraylength = "arraylength";
    public static final String name_athrow = "athrow";
    public static final String name_checkcast = "checkcast";
    public static final String name_instanceof = "instanceof";
    public static final String name_monitorenter = "monitorenter";
    public static final String name_monitorexit = "monitorexit";
    public static final String name_wide = "wide";
    public static final String name_wide_iload = "wide iload";
    public static final String name_wide_lload = "wide lload";
    public static final String name_wide_fload = "wide fload";
    public static final String name_wide_dload = "wide dload";
    public static final String name_wide_aload = "wide aload";
    public static final String name_wide_istore = "wide istore";
    public static final String name_wide_lstore = "wide lstore";
    public static final String name_wide_fstore = "wide fstore";
    public static final String name_wide_dstore = "wide dstore";
    public static final String name_wide_astore = "wide astore";
    public static final String name_wide_iinc = "wide iinc";
    public static final String name_wide_ret = "wide ret";
    public static final String name_multianewarray = "multianewarray";
    public static final String name_ifnull = "ifnull";
    public static final String name_ifnonnull = "ifnonnull";
    public static final String name_goto_w = "goto_w";
    public static final String name_jsr_w = "jsr_w";
    // Quick opcodes
    // Reserved opcodes
    public static final int op_breakpoint = 202;
    public static final int op_impdep1 = 254;
    public static final int op_impdep2 = 255;
    public static final String name_breakpoint = "breakpoint";
    public static final String name_impdep1 = "impdep1";
    public static final String name_impdep2 = "impdep2";
    
}
