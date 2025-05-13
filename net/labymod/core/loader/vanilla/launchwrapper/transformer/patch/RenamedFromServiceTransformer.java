// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch;

import java.net.URL;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import net.minecraft.launchwrapper.Launch;
import net.labymod.core.rename.RenamedFromService;
import net.minecraft.launchwrapper.IClassTransformer;

public class RenamedFromServiceTransformer implements IClassTransformer
{
    private final RenamedFromService renamedFromService;
    
    public RenamedFromServiceTransformer() {
        (this.renamedFromService = new RenamedFromService("labymod", (ClassLoader)Launch.classLoader, name -> Launch.classLoader.loadResource(name))).load();
    }
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        final Remapper remapperMappings = this.renamedFromService.getRemapper();
        if (remapperMappings == null) {
            return classData;
        }
        final ClassReader reader = new ClassReader(classData);
        final ClassNode node = new ClassNode();
        final ClassRemapper remapper = new ClassRemapper((ClassVisitor)node, remapperMappings);
        reader.accept((ClassVisitor)remapper, 0);
        final ClassWriter writer = ASMHelper.newClassWriter(reader, false);
        node.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
}
