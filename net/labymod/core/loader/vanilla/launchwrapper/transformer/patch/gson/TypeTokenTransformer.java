// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.gson;

import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.volt.asm.util.OpcodesUtil;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class TypeTokenTransformer implements IClassTransformer
{
    private static final String PARAMETERIZED_NAME = "getParameterized";
    private static final String PARAMETERIZED_DESCRIPTOR = "(Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;";
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!name.equals("com.google.gson.reflect.TypeToken")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private void patch(final ClassNode node) {
        boolean foundParameterized = false;
        for (final MethodNode method : node.methods) {
            if (method.name.equals("getParameterized") && method.desc.equals("(Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;")) {
                foundParameterized = true;
            }
        }
        if (!foundParameterized) {
            node.methods.add(ASMHelper.createMethod(OpcodesUtil.publicAccess(true), "getParameterized", "(Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;", null, null, methodNode -> {
                methodNode.visitTypeInsn(187, "com/google/gson/reflect/TypeToken");
                methodNode.visitInsn(89);
                methodNode.visitInsn(1);
                methodNode.visitVarInsn(25, 0);
                methodNode.visitVarInsn(25, 1);
                methodNode.visitMethodInsn(184, "com/google/gson/internal/$Gson$Types", "newParameterizedTypeWithOwner", "(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/ParameterizedType;", false);
                methodNode.visitMethodInsn(183, "com/google/gson/reflect/TypeToken", "<init>", "(Ljava/lang/reflect/Type;)V", false);
                methodNode.visitInsn(176);
            }));
        }
    }
}
