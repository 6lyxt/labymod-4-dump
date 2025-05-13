// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.instruction;

import java.util.Iterator;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.util.function.BiConsumer;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class InstructionFinder
{
    public AbstractInsnNode findFirstInstruction(final MethodNode node, final int opcode) {
        for (final AbstractInsnNode instruction : node.instructions) {
            if (instruction.getOpcode() == opcode) {
                return instruction;
            }
        }
        return null;
    }
    
    public AbstractInsnNode findLastReturnInstruction(final MethodNode methodNode) {
        AbstractInsnNode target = null;
        for (final AbstractInsnNode node : methodNode.instructions) {
            if (OpcodesUtil.isReturnOpcode(node.getOpcode())) {
                target = node;
            }
        }
        return target;
    }
    
    public void findConstructor(final ClassNode node, final BiConsumer<MethodNode, Boolean> callback) {
        boolean found = false;
        for (final MethodNode method : node.methods) {
            final String methodName = ASMHelper.getNameWithDesc(method);
            if (methodName.startsWith("<init>")) {
                callback.accept(method, false);
                found = true;
            }
        }
        if (found) {
            return;
        }
        final MethodNode newPublicConstructor = ASMHelper.createPublicConstructor();
        node.methods.add(newPublicConstructor);
        callback.accept(newPublicConstructor, true);
    }
    
    public void findStaticConstructor(final ClassNode node, final BiConsumer<MethodNode, Boolean> callback) {
        boolean found = false;
        for (final MethodNode method : node.methods) {
            final String methodName = ASMHelper.getNameWithDesc(method);
            if (methodName.startsWith("<clinit>")) {
                callback.accept(method, false);
                found = true;
            }
        }
        if (found) {
            return;
        }
        final MethodNode newStaticConstructor = ASMHelper.createStaticConstructor();
        node.methods.add(newStaticConstructor);
        callback.accept(newStaticConstructor, true);
    }
}
