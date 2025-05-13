// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.mixin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.spongepowered.asm.transformers.MixinClassReader;
import org.objectweb.asm.tree.ClassNode;
import java.io.IOException;
import net.labymod.core.loader.ReflectLabyModLoader;
import java.io.InputStream;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;

public class LabyModMixinService extends MixinServiceLaunchWrapper
{
    public String getName() {
        return "LabyMod";
    }
    
    public MixinEnvironment.Phase getInitialPhase() {
        return MixinEnvironment.Phase.PREINIT;
    }
    
    public boolean isValid() {
        return true;
    }
    
    public InputStream getResourceAsStream(final String name) {
        return ReflectLabyModLoader.invokeGetMixinServiceResourceAsStream(name, super.getResourceAsStream(name));
    }
    
    public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
        return MixinEnvironment.CompatibilityLevel.JAVA_21;
    }
    
    public byte[] getClassBytes(final String name, final String transformedName) throws IOException {
        return ReflectLabyModLoader.invokeGetMixinServiceClassBytes(name, transformedName, super.getClassBytes(name, transformedName));
    }
    
    public void checkEnv(final Object bootSource) {
    }
    
    public ClassNode getClassNode(final String className) throws ClassNotFoundException, IOException {
        return this.getClassNode(className, true);
    }
    
    public ClassNode getClassNode(final String className, final boolean runTransformers) throws ClassNotFoundException, IOException {
        final ClassReader classReader = (ClassReader)new MixinClassReader(this.getClassBytes(className, runTransformers), className);
        final ClassNode classNode = new ClassNode();
        classReader.accept((ClassVisitor)classNode, 0);
        return classNode;
    }
}
