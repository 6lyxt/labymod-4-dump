// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.tree;

import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.Handle;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;

public class InsnListBuilder
{
    private final InsnList list;
    
    private InsnListBuilder() {
        this.list = new InsnList();
    }
    
    @NotNull
    public static InsnListBuilder create() {
        return new InsnListBuilder();
    }
    
    public InsnListBuilder addType(final int opcode, final String type) {
        this.list.add((AbstractInsnNode)new TypeInsnNode(opcode, type));
        return this;
    }
    
    public InsnListBuilder addIINC(final int varIndex, final int increment) {
        this.list.add((AbstractInsnNode)new IincInsnNode(varIndex, increment));
        return this;
    }
    
    public InsnListBuilder addInt(final int opcode, final int operand) {
        this.list.add((AbstractInsnNode)new IntInsnNode(opcode, operand));
        return this;
    }
    
    public InsnListBuilder addInvokeDynamic(final String name, final String descriptor, final Handle bootstrapMethodHandle, final Object... bootstrapMethodArguments) {
        this.list.add((AbstractInsnNode)new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
        return this;
    }
    
    public InsnListBuilder addJump(final int opcode, final LabelNode label) {
        this.list.add((AbstractInsnNode)new JumpInsnNode(opcode, label));
        return this;
    }
    
    public InsnListBuilder addLDC(final Object value) {
        this.list.add((AbstractInsnNode)new LdcInsnNode(value));
        return this;
    }
    
    public InsnListBuilder addLookupSwitch(final LabelNode defaultHandler, final int[] keys, final LabelNode[] labels) {
        this.list.add((AbstractInsnNode)new LookupSwitchInsnNode(defaultHandler, keys, labels));
        return this;
    }
    
    public InsnListBuilder addMethod(final int opcode, final String owner, final String name, final String descriptor) {
        return this.addMethod(opcode, owner, name, descriptor, false);
    }
    
    public InsnListBuilder addMethod(final int opcode, final String owner, final String name, final String descriptor, final boolean isInterface) {
        this.list.add((AbstractInsnNode)new MethodInsnNode(opcode, owner, name, descriptor, isInterface));
        return this;
    }
    
    public InsnListBuilder addField(final int opcode, final String owner, final String name, final String descriptor) {
        this.list.add((AbstractInsnNode)new FieldInsnNode(opcode, owner, name, descriptor));
        return this;
    }
    
    public InsnListBuilder addVar(final int opcode, final int varIndex) {
        this.list.add((AbstractInsnNode)new VarInsnNode(opcode, varIndex));
        return this;
    }
    
    public InsnListBuilder addInstruction(final int opcode) {
        this.list.add((AbstractInsnNode)new InsnNode(opcode));
        return this;
    }
    
    public InsnListBuilder addMultiDimensionalArray(final String descriptor, final int numDimensions) {
        this.list.add((AbstractInsnNode)new MultiANewArrayInsnNode(descriptor, numDimensions));
        return this;
    }
    
    public InsnListBuilder addTableSwitch(final int min, final int max, final LabelNode defaultHandler, final LabelNode... labels) {
        this.list.add((AbstractInsnNode)new TableSwitchInsnNode(min, max, defaultHandler, labels));
        return this;
    }
    
    public InsnListBuilder add(final AbstractInsnNode node) {
        this.list.add(node);
        return this;
    }
    
    public InsnListBuilder add(final InsnList list) {
        this.list.add(list);
        return this;
    }
    
    public InsnListBuilder add(final InsnListBuilder builder) {
        return this.add(builder.build());
    }
    
    public InsnList build() {
        return this.list;
    }
}
