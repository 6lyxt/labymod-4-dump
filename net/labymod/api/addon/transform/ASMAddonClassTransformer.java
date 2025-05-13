// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.transform;

import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;

public interface ASMAddonClassTransformer extends AddonClassTransformer
{
    default byte[] transform(final String name, final String transformedName, final byte[] classBytes) {
        return ASMHelper.transformClassData(classBytes, node -> this.transform(name, transformedName, node));
    }
    
    void transform(final String p0, final String p1, final ClassNode p2);
}
