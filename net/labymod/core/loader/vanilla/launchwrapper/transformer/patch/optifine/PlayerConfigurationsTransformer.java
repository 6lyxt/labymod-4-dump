// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.optifine;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class PlayerConfigurationsTransformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null || !name.equals("net.optifine.player.PlayerConfigurations")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private void patch(final ClassNode node) {
        for (final MethodNode method : node.methods) {
            if (!method.name.equals("<init>")) {
                if (method.name.equals("<clinit>")) {
                    continue;
                }
                final Type returnType = Type.getReturnType(method.desc);
                method.instructions.clear();
                if (returnType == Type.VOID_TYPE) {
                    method.instructions.add((AbstractInsnNode)new InsnNode(177));
                }
                else {
                    method.instructions.add((AbstractInsnNode)new InsnNode(1));
                    method.instructions.add((AbstractInsnNode)new InsnNode(176));
                }
            }
        }
    }
}
