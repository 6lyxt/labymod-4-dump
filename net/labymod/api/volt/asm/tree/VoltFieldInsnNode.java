// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.tree;

import org.objectweb.asm.tree.FieldInsnNode;

public class VoltFieldInsnNode
{
    private final FieldInsnNode field;
    
    public VoltFieldInsnNode(final int opcode, final String owner, final String name, final String desc) {
        this(new FieldInsnNode(opcode, owner, name, desc));
    }
    
    public VoltFieldInsnNode(final FieldInsnNode field) {
        this.field = field;
    }
    
    public FieldInsnNode getField() {
        return this.field;
    }
    
    public FieldInsnNode copy() {
        return new FieldInsnNode(this.field.getOpcode(), this.field.owner, this.field.name, this.field.desc);
    }
}
