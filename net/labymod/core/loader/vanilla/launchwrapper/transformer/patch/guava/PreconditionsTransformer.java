// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import java.util.Iterator;
import java.util.Collection;
import org.objectweb.asm.Label;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class PreconditionsTransformer implements IClassTransformer
{
    private static final String CHECK_ARGUMENT_NAME = "checkArgument";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_SINGLE_OBJECT = "(ZLjava/lang/String;Ljava/lang/Object;)V";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_TWO_OBJECTS = "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V";
    private static final String CHECK_ARGUMENT_DESCRIPTOR_THREE_OBJECTS = "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V";
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!name.equals("com.google.common.base.Preconditions")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private void patch(final ClassNode node) {
        boolean foundCheckArgumentSingleObject = false;
        boolean foundCheckArgumentTwoObjects = false;
        boolean foundCheckArgumentThreeObjects = false;
        for (final MethodNode method : node.methods) {
            if (method.name.equals("checkArgument") && method.desc.equals("(ZLjava/lang/String;Ljava/lang/Object;)V")) {
                foundCheckArgumentSingleObject = true;
            }
            else if (method.name.equals("checkArgument") && method.desc.equals("(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V")) {
                foundCheckArgumentTwoObjects = true;
            }
            else {
                if (!method.name.equals("checkArgument") || !method.desc.equals("(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V")) {
                    continue;
                }
                foundCheckArgumentThreeObjects = true;
            }
        }
        if (!foundCheckArgumentSingleObject) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), "checkArgument", "(ZLjava/lang/String;Ljava/lang/Object;)V", null, null, methodNode -> {
                methodNode.visitCode();
                methodNode.visitVarInsn(21, 0);
                final Label label = new Label();
                methodNode.visitJumpInsn(154, label);
                methodNode.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode.visitInsn(89);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitInsn(4);
                methodNode.visitTypeInsn(189, "java/lang/Object");
                methodNode.visitInsn(89);
                methodNode.visitInsn(3);
                methodNode.visitVarInsn(25, 2);
                methodNode.visitInsn(83);
                methodNode.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode.visitInsn(191);
                methodNode.visitLabel(label);
                methodNode.visitInsn(177);
                methodNode.visitEnd();
                return;
            }));
        }
        if (!foundCheckArgumentTwoObjects) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), "checkArgument", "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", null, null, methodNode -> {
                methodNode.visitCode();
                methodNode.visitVarInsn(21, 0);
                final Label label2 = new Label();
                methodNode.visitJumpInsn(154, label2);
                methodNode.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode.visitInsn(89);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitInsn(5);
                methodNode.visitTypeInsn(189, "java/lang/Object");
                methodNode.visitInsn(89);
                methodNode.visitInsn(3);
                methodNode.visitVarInsn(25, 2);
                methodNode.visitInsn(83);
                methodNode.visitInsn(89);
                methodNode.visitInsn(4);
                methodNode.visitVarInsn(25, 3);
                methodNode.visitInsn(83);
                methodNode.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode.visitInsn(191);
                methodNode.visitLabel(label2);
                methodNode.visitInsn(177);
                methodNode.visitEnd();
                return;
            }));
        }
        if (!foundCheckArgumentThreeObjects) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), "checkArgument", "(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", null, null, methodNode -> {
                methodNode.visitCode();
                methodNode.visitVarInsn(21, 0);
                final Label label3 = new Label();
                methodNode.visitJumpInsn(154, label3);
                methodNode.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodNode.visitInsn(89);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitInsn(5);
                methodNode.visitTypeInsn(189, "java/lang/Object");
                methodNode.visitInsn(89);
                methodNode.visitInsn(3);
                methodNode.visitVarInsn(25, 2);
                methodNode.visitInsn(83);
                methodNode.visitInsn(89);
                methodNode.visitInsn(4);
                methodNode.visitVarInsn(25, 3);
                methodNode.visitInsn(83);
                methodNode.visitInsn(89);
                methodNode.visitInsn(5);
                methodNode.visitVarInsn(25, 4);
                methodNode.visitInsn(83);
                methodNode.visitMethodInsn(184, "com/google/common/base/Preconditions", "format", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", false);
                methodNode.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
                methodNode.visitInsn(191);
                methodNode.visitLabel(label3);
                methodNode.visitInsn(177);
                methodNode.visitEnd();
            }));
        }
    }
}
