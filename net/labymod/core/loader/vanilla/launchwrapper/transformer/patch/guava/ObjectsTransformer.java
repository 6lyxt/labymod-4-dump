// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import java.util.Iterator;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class ObjectsTransformer implements IClassTransformer
{
    private static final String FIRST_NON_NULL_NAME = "firstNonNull";
    private static final String FIRST_NON_NULL_DESCRIPTOR = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!name.equals("com.google.common.base.Objects")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::addFirstNonNull);
    }
    
    private void addFirstNonNull(final ClassNode classNode) {
        boolean found = false;
        for (final MethodNode method : classNode.methods) {
            if (method.name.equals("firstNonNull") && method.desc.equals("(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")) {
                found = true;
                break;
            }
        }
        if (found) {
            return;
        }
        final MethodNode firstNonNullMethod = new MethodNode(9, "firstNonNull", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "<T:Ljava/lang/Object;>(TT;TT;)TT;", (String[])null);
        firstNonNullMethod.visitCode();
        final Label label0 = new Label();
        firstNonNullMethod.visitLabel(label0);
        firstNonNullMethod.visitLineNumber(56, label0);
        firstNonNullMethod.visitVarInsn(25, 0);
        final Label label2 = new Label();
        firstNonNullMethod.visitJumpInsn(198, label2);
        firstNonNullMethod.visitVarInsn(25, 0);
        final Label label3 = new Label();
        firstNonNullMethod.visitJumpInsn(167, label3);
        firstNonNullMethod.visitLabel(label2);
        firstNonNullMethod.visitFrame(3, 0, (Object[])null, 0, (Object[])null);
        firstNonNullMethod.visitVarInsn(25, 1);
        firstNonNullMethod.visitMethodInsn(184, "com/google/common/base/Preconditions", "checkNotNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
        firstNonNullMethod.visitLabel(label3);
        firstNonNullMethod.visitFrame(4, 0, (Object[])null, 1, new Object[] { "java/lang/Object" });
        firstNonNullMethod.visitInsn(176);
        final Label label4 = new Label();
        firstNonNullMethod.visitLabel(label4);
        firstNonNullMethod.visitLocalVariable("first", "Ljava/lang/Object;", "TT;", label0, label4, 0);
        firstNonNullMethod.visitLocalVariable("second", "Ljava/lang/Object;", "TT;", label0, label4, 1);
        firstNonNullMethod.visitMaxs(1, 2);
        firstNonNullMethod.visitEnd();
        classNode.methods.add(firstNonNullMethod);
    }
}
