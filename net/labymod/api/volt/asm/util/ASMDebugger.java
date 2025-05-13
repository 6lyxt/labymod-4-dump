// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.util;

import java.io.PrintStream;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.Type;

public final class ASMDebugger
{
    private static final Type SYSTEM_TYPE;
    private static final Type PRINT_STREAM_TYPE;
    private static final Type STRING_TYPE;
    private static final Type VOID_TYPE;
    private static final String OUT_NAME = "out";
    private static final String PRINTLN_NAME = "println";
    private static final String PRINTLN_DESCRIPTOR;
    
    public static InsnList buildPrintStatementInstructions(final String content) {
        return InsnListBuilder.create().addField(178, ASMDebugger.SYSTEM_TYPE.getInternalName(), "out", ASMDebugger.PRINT_STREAM_TYPE.getDescriptor()).addLDC(content).addMethod(182, ASMDebugger.PRINT_STREAM_TYPE.getInternalName(), "println", ASMDebugger.PRINTLN_DESCRIPTOR).build();
    }
    
    static {
        SYSTEM_TYPE = Type.getType((Class)System.class);
        PRINT_STREAM_TYPE = Type.getType((Class)PrintStream.class);
        STRING_TYPE = Type.getType((Class)String.class);
        VOID_TYPE = Type.VOID_TYPE;
        PRINTLN_DESCRIPTOR = Type.getMethodDescriptor(ASMDebugger.VOID_TYPE, new Type[] { ASMDebugger.STRING_TYPE });
    }
}
