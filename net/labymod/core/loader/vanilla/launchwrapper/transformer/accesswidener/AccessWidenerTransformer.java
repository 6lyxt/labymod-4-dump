// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import org.objectweb.asm.ClassVisitor;
import org.spongepowered.asm.util.asm.ASM;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.api.loader.platform.PlatformClassTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class AccessWidenerTransformer implements IClassTransformer, PlatformClassTransformer
{
    private final AccessWidener accessWidener;
    
    public AccessWidenerTransformer(final AccessWidener accessWidener) {
        this.accessWidener = accessWidener;
    }
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!this.accessWidener.containsClass(name)) {
            return classData;
        }
        final ClassReader classReader = new ClassReader(classData);
        ClassVisitor visitor;
        final ClassWriter classWriter = (ClassWriter)(visitor = (ClassVisitor)new ClassWriter(0));
        visitor = AccessWidenerVisitor.of(ASM.API_VERSION, visitor, this.accessWidener);
        classReader.accept(visitor, 0);
        return classWriter.toByteArray();
    }
}
