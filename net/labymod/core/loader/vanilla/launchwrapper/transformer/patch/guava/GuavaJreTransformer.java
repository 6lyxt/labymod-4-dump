// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.guava;

import org.objectweb.asm.tree.AbstractInsnNode;
import java.util.Iterator;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;

public class GuavaJreTransformer implements IClassTransformer
{
    private final Map<String, String> names;
    private final Map<String, List<String>> excludes;
    
    public GuavaJreTransformer() {
        this.excludes = new HashMap<String, List<String>>();
        this.addExclude("com/google/common/base/Objects", "equal(Ljava/lang/Object;Ljava/lang/Object;)Z");
        this.addExclude("com/google/common/base/Objects", "hashCode([Ljava/lang/Object;)I");
        (this.names = new HashMap<String, String>()).put("com/google/common/base/Objects", "com/google/common/base/MoreObjects");
        this.names.put("com/google/common/base/Objects$ToStringHelper", "com/google/common/base/MoreObjects$ToStringHelper");
    }
    
    private void addExclude(final String owner, final String methodDescriptor) {
        this.excludes.computeIfAbsent(owner, l -> new ArrayList()).add(methodDescriptor);
    }
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private void patch(final ClassNode node) {
        for (MethodNode method : node.methods) {
            for (int i = 0; i < method.instructions.size(); ++i) {
                final AbstractInsnNode instruction = method.instructions.get(i);
                if (instruction instanceof final MethodInsnNode methodInsnNode) {
                    final String name = methodInsnNode.name + methodInsnNode.desc;
                    if (!this.shouldExclude(methodInsnNode.owner, name)) {
                        for (final Map.Entry<String, String> entry : this.names.entrySet()) {
                            if (methodInsnNode.owner.equals(entry.getKey())) {
                                methodInsnNode.owner = entry.getValue();
                                methodInsnNode.desc = this.replace(methodInsnNode.desc);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean shouldExclude(final String owner, final String name) {
        final List<String> list = this.excludes.get(owner);
        if (list == null) {
            return false;
        }
        for (final String exclude : list) {
            if (exclude.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private String replace(final String value) {
        for (final Map.Entry<String, String> entry : this.names.entrySet()) {
            if (value.contains(entry.getKey())) {
                return value.replace(entry.getKey(), entry.getValue());
            }
        }
        return value;
    }
}
