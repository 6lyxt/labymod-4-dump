// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import net.labymod.accesswidener.access.accesses.MethodAccess;
import net.labymod.accesswidener.access.Access;
import org.objectweb.asm.MethodVisitor;

public class AccessWidenerMethodVisitor extends MethodVisitor
{
    private final AccessWidenerVisitor visitor;
    private final Access access;
    
    public AccessWidenerMethodVisitor(final int api, final MethodVisitor methodVisitor, final AccessWidenerVisitor visitor, final Access access) {
        super(api, methodVisitor);
        this.visitor = visitor;
        this.access = access;
    }
    
    public void visitMethodInsn(int opcode, final String owner, final String name, final String descriptor, final boolean isInterface) {
        if (opcode == 183 && owner.equals(this.visitor.className) && !name.equals("<init>") && this.access != MethodAccess.DEFAULT) {
            opcode = 182;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
