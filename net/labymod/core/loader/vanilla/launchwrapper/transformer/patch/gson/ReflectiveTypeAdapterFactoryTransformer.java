// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.gson;

import java.util.ListIterator;
import java.util.Iterator;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.util.Objects;
import org.objectweb.asm.tree.MethodNode;
import java.util.function.Function;
import net.minecraft.launchwrapper.IClassTransformer;

public class ReflectiveTypeAdapterFactoryTransformer implements IClassTransformer
{
    private static final String NAME = "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1";
    private static final String GSON_2_11_0_NAME = "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$2";
    private static final String READ_NAME = "read";
    private static final String READ_DESC = "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V";
    private static final String READ_INTO_FIELD = "readIntoField";
    private static final String READ_INFO_FIELD_DESC = "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V";
    private static final Function<MethodNode, Boolean> READ_FUNCTION;
    private static final Function<MethodNode, Boolean> READ_INFO_FIELD_FUNCTION;
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!Objects.equals(name, "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1") && !Objects.equals(name, "com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$2")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private void patch(final ClassNode classNode) {
        for (final MethodNode method : classNode.methods) {
            if (ReflectiveTypeAdapterFactoryTransformer.READ_FUNCTION.apply(method) || ReflectiveTypeAdapterFactoryTransformer.READ_INFO_FIELD_FUNCTION.apply(method)) {
                AbstractInsnNode lastGetFieldInstruction = null;
                for (final AbstractInsnNode instruction : method.instructions) {
                    if (instruction.getOpcode() == 182) {
                        lastGetFieldInstruction = instruction;
                    }
                }
                if (lastGetFieldInstruction == null) {
                    continue;
                }
                method.instructions.set(lastGetFieldInstruction, (AbstractInsnNode)new MethodInsnNode(184, "net/labymod/core/util/gson/ReflectiveTypeAdapterFactoryExtension", "handleCustomField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"));
            }
        }
    }
    
    static {
        READ_FUNCTION = (method -> Objects.equals(method.name, "read") && Objects.equals(method.desc, "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V"));
        READ_INFO_FIELD_FUNCTION = (methodNode -> Objects.equals(methodNode.name, "readIntoField") && Objects.equals(methodNode.desc, "(Lcom/google/gson/stream/JsonReader;Ljava/lang/Object;)V"));
    }
}
