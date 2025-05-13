// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import net.labymod.accesswidener.bytecode.BytecodeProvider;

public class ASMBytecodeProvider implements BytecodeProvider
{
    public int makePublic(final int access) {
        return (access & 0xFFFFFFF9) | 0x1;
    }
    
    public int makeProtected(final int access) {
        if ((access & 0x1) != 0x0) {
            return access;
        }
        return (access & 0xFFFFFFFD) | 0x4;
    }
    
    public int makeFinalIfPrivate(final int access, final String name, final int ownerAccess) {
        if (name.equals("<init>") || name.equals("<clint>")) {
            return access;
        }
        if ((ownerAccess & 0x200) != 0x0 || (access & 0x8) != 0x0) {
            return access;
        }
        if ((access & 0x2) != 0x0) {
            return access | 0x10;
        }
        return access;
    }
    
    public int removeFinal(final int access) {
        return access & 0xFFFFFFEF;
    }
}
