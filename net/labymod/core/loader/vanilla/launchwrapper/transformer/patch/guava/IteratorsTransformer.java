// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import java.util.Iterator;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class IteratorsTransformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!name.equals("com.google.common.collect.Iterators")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::handleClassNode);
    }
    
    private void handleClassNode(final ClassNode classNode) {
        for (final MethodNode method : classNode.methods) {
            if (!method.name.equals("<init>")) {
                if (method.name.equals("<clinit>")) {
                    continue;
                }
                method.access = 9;
            }
        }
    }
}
