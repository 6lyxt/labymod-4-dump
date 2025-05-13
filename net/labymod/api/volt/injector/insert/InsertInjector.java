// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.injector.insert;

import java.util.UUID;
import org.objectweb.asm.tree.FieldInsnNode;
import java.util.function.BiConsumer;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import net.labymod.api.volt.asm.primitive.Primitive;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.primitive.Primitives;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import net.labymod.api.volt.asm.tree.VoltFieldInsnNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import net.labymod.api.volt.asm.tree.VoltInsnList;
import org.objectweb.asm.tree.FieldNode;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import net.labymod.api.volt.asm.exception.VoltException;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import net.labymod.api.volt.callback.JumpStatement;
import org.spongepowered.asm.mixin.injection.struct.Target;
import net.labymod.api.volt.asm.instruction.InstructionFinder;
import org.spongepowered.asm.mixin.injection.code.Injector;

public class InsertInjector extends Injector
{
    private static final String FAST_CALLBACK_INFO_RETURNABLE_NAME = "net/labymod/api/volt/callback/InsertInfoReturnable";
    private final InstructionFinder instructionFinder;
    private Target.Extension invokeExtension;
    private final boolean cancellable;
    private final JumpStatement jumpStatement;
    
    public InsertInjector(final InjectionInfo info, final boolean cancellable, final JumpStatement jumpStatement) {
        super(info, "@Insert");
        this.cancellable = cancellable;
        this.jumpStatement = jumpStatement;
        this.instructionFinder = new InstructionFinder();
    }
    
    protected void inject(final Target target, final InjectionNodes.InjectionNode node) {
        (this.invokeExtension = target.extendStack()).add(target.arguments.length);
        this.injectAt(target, this.info.getMethod(), node);
        this.invokeExtension.apply();
    }
    
    private void injectAt(final Target target, final MethodNode instruction, final InjectionNodes.InjectionNode injectionNode) {
        final ClassNode targetClassNode = target.classNode;
        final MethodNode targetMethod = target.method;
        final String methodNameWithDesc = instruction.name + instruction.desc;
        final String invokeMethodDescriptor = methodNameWithDesc.substring(methodNameWithDesc.indexOf(40));
        final Type type = ASMHelper.getLastParameter(invokeMethodDescriptor);
        if (type == null) {
            throw new VoltException("The method to be invoked is invalid. Missing parameters.");
        }
        final String insertInfoName = type.getInternalName();
        final String fieldName = this.generateCallbackInfoReturnableFieldName(instruction.name);
        targetClassNode.fields.add(0, new FieldNode(OpcodesUtil.privateAccess(this.isStatic), fieldName, "L" + insertInfoName, (String)null, (Object)null));
        this.initializeGeneratedField(this.isStatic, targetClassNode, fieldName, insertInfoName, this.cancellable);
        final VoltInsnList injection = new VoltInsnList(this.isStatic);
        injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
        final Type[] argumentTypes = Type.getArgumentTypes(targetMethod.desc);
        int currentIndex = 0;
        for (final Type argumentType : argumentTypes) {
            ++currentIndex;
            final VarInsnNode node = new VarInsnNode(argumentType.getOpcode(21), this.isStatic ? (currentIndex - 1) : currentIndex);
            injection.add((AbstractInsnNode)node);
            final String className = argumentType.getClassName();
            if (className.equals("double") || className.equals("long")) {
                ++currentIndex;
            }
        }
        final VoltFieldInsnNode generatedField = new VoltFieldInsnNode(OpcodesUtil.getField(this.isStatic), targetClassNode.name, fieldName, "L" + insertInfoName);
        injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
        injection.add((AbstractInsnNode)generatedField.copy());
        injection.add((AbstractInsnNode)new MethodInsnNode(OpcodesUtil.invokeMethod(this.isStatic), targetClassNode.name, methodNameWithDesc.substring(0, methodNameWithDesc.indexOf(40)), invokeMethodDescriptor));
        if (this.jumpStatement == JumpStatement.BREAK || this.jumpStatement == JumpStatement.CONTINUE) {
            final LabelNode continueLabel = this.findLabel(this.jumpStatement, injectionNode);
            if (continueLabel != null) {
                injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode)generatedField.copy());
                injection.add((AbstractInsnNode)new MethodInsnNode(182, insertInfoName, "isJumping", "()Z"));
                final LabelNode label = new LabelNode();
                injection.add((AbstractInsnNode)new JumpInsnNode(153, label));
                injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode)generatedField.copy());
                injection.add((AbstractInsnNode)new MethodInsnNode(182, insertInfoName, "reset", "()V"));
                injection.add((AbstractInsnNode)new JumpInsnNode(167, continueLabel));
                injection.add((AbstractInsnNode)label);
                injection.add((AbstractInsnNode)new FrameNode(3, 0, (Object[])null, 1, (Object[])null));
            }
        }
        else if (this.cancellable) {
            injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
            injection.add((AbstractInsnNode)generatedField.copy());
            injection.add((AbstractInsnNode)new MethodInsnNode(182, insertInfoName, "isCancelled", "()Z"));
            final LabelNode label2 = new LabelNode();
            injection.add((AbstractInsnNode)new JumpInsnNode(153, label2));
            if (this.isReturnable(insertInfoName)) {
                injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
                injection.add((AbstractInsnNode)generatedField.copy());
                injection.add((AbstractInsnNode)new MethodInsnNode(182, insertInfoName, "getReturnValue", "()Ljava/lang/Object;"));
            }
            injection.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
            injection.add((AbstractInsnNode)generatedField.copy());
            injection.add((AbstractInsnNode)new MethodInsnNode(182, insertInfoName, "reset", "()V"));
            final Type returnType = Type.getReturnType(targetMethod.desc);
            if (Primitives.isPrimitive(returnType)) {
                this.primitiveCheckCast(injection, Primitives.getPrimitive(returnType));
            }
            else {
                final String descriptor = returnType.getDescriptor();
                if (!descriptor.startsWith("L")) {
                    throw new VoltException("Illegal return type: " + String.valueOf(returnType));
                }
                this.objectCheckCast(injection, returnType.getInternalName());
            }
            injection.add((AbstractInsnNode)label2);
        }
        this.invokeExtension.add(injection.stackSize());
        this.invokeHandler(injection.getList(), injectionNode, targetMethod);
    }
    
    private LabelNode findLabel(final JumpStatement jumpStatement, final InjectionNodes.InjectionNode injectionNode) {
        if (jumpStatement == JumpStatement.RETURN) {
            throw new IllegalArgumentException("Jump statement RETURN is not allowed");
        }
        final AbstractInsnNode currentTarget = injectionNode.getCurrentTarget();
        LabelNode label;
        if (jumpStatement == JumpStatement.CONTINUE) {
            final AbstractInsnNode instruction = ASMHelper.findInstruction(currentTarget, true, node -> node.getOpcode() == 167);
            if (!(instruction instanceof JumpInsnNode)) {
                throw new IllegalStateException("Could not find a jump instruction");
            }
            final JumpInsnNode jumpInsnNode = (JumpInsnNode)instruction;
            label = jumpInsnNode.label;
        }
        else {
            final AbstractInsnNode frameInstruction = ASMHelper.findInstruction(currentTarget, false, node -> node instanceof FrameNode);
            if (frameInstruction == null) {
                throw new IllegalStateException("Could not find any FrameNode");
            }
            final AbstractInsnNode jumpInstruction = ASMHelper.findInstruction(frameInstruction, true, node -> node instanceof JumpInsnNode);
            if (!(jumpInstruction instanceof JumpInsnNode)) {
                throw new IllegalStateException("Could not find a jump instruction");
            }
            final JumpInsnNode jumpInsnNode2 = (JumpInsnNode)jumpInstruction;
            label = jumpInsnNode2.label;
        }
        return label;
    }
    
    @NotNull
    public AbstractInsnNode invokeHandler(@NotNull final InsnList list, @NotNull final InjectionNodes.InjectionNode node, @NotNull final MethodNode handler) {
        handler.instructions.insertBefore(node.getCurrentTarget(), list);
        final boolean isPrivate = (handler.access & 0x2) != 0x0;
        final int invokeOpcode = this.isStatic ? 184 : (isPrivate ? 183 : 182);
        final MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
        list.add((AbstractInsnNode)insn);
        this.info.addCallbackInvocation(handler);
        return (AbstractInsnNode)insn;
    }
    
    private void primitiveCheckCast(final VoltInsnList injection, final Primitive primitive) {
        if (!primitive.equals(Primitives.VOID)) {
            injection.add((AbstractInsnNode)new TypeInsnNode(192, primitive.getInternalName()));
            injection.add((AbstractInsnNode)new MethodInsnNode(182, primitive.getInternalName(), primitive.getMethodName(), primitive.getMethodDescriptor()));
        }
        injection.add((AbstractInsnNode)new InsnNode(primitive.getReturnOpcode()));
    }
    
    private void objectCheckCast(final VoltInsnList injection, final String internalName) {
        injection.add((AbstractInsnNode)new TypeInsnNode(192, internalName));
        injection.add((AbstractInsnNode)new InsnNode(176));
    }
    
    private boolean isReturnable(final String internalName) {
        return internalName.equals("net/labymod/api/volt/callback/InsertInfoReturnable");
    }
    
    private void initializeGeneratedField(final boolean isStatic, final ClassNode classNode, final String fieldName, final String internalName, final boolean cancellable) {
        if (isStatic) {
            this.instructionFinder.findStaticConstructor(classNode, this.initializeField(classNode, fieldName, internalName, true, cancellable));
        }
        else {
            this.instructionFinder.findConstructor(classNode, this.initializeField(classNode, fieldName, internalName, false, cancellable));
        }
    }
    
    private BiConsumer<MethodNode, Boolean> initializeField(final ClassNode classNode, final String fieldName, final String internalName, final boolean isStatic, final boolean cancellable) {
        return (methodNode, created) -> {
            final VoltInsnList list = new VoltInsnList(isStatic);
            list.addIfNotStatic((AbstractInsnNode)new VarInsnNode(25, 0));
            list.add((AbstractInsnNode)new TypeInsnNode(187, internalName));
            list.add((AbstractInsnNode)new InsnNode(89));
            new InsnNode(cancellable ? 4 : 3);
            final InsnNode node;
            final Object o;
            ((VoltInsnList)o).add((AbstractInsnNode)node);
            list.add((AbstractInsnNode)new MethodInsnNode(183, internalName, "<init>", "(Z)V"));
            list.add((AbstractInsnNode)new FieldInsnNode(OpcodesUtil.putField(isStatic), classNode.name, fieldName, "L" + internalName));
            if (created) {
                list.add((AbstractInsnNode)new InsnNode(177));
                methodNode.instructions.insert(list.getList());
            }
            else {
                final AbstractInsnNode instruction = this.instructionFinder.findFirstInstruction(methodNode, 183);
                if (instruction == null) {
                    final AbstractInsnNode instruction2 = this.instructionFinder.findLastReturnInstruction(methodNode);
                    methodNode.instructions.insertBefore(instruction2, list.getList());
                    this.increaseConstructorMaxStackSize(methodNode, list.stackSize());
                    return;
                }
                else {
                    methodNode.instructions.insert(instruction, list.getList());
                }
            }
            this.increaseConstructorMaxStackSize(methodNode, list.stackSize());
        };
    }
    
    private void increaseConstructorMaxStackSize(final MethodNode node, final int count) {
        node.maxStack += count;
    }
    
    private String generateCallbackInfoReturnableFieldName(final String name) {
        return name + UUID.randomUUID().toString().replace("-", "").substring(16);
    }
}
