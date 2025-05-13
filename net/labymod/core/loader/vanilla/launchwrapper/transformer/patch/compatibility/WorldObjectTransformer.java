// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.compatibility;

import java.net.URL;
import org.objectweb.asm.ClassWriter;
import java.util.Iterator;
import net.labymod.api.volt.rename.ClassInfo;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import net.minecraft.launchwrapper.Launch;
import net.labymod.api.volt.rename.ClassProvider;
import net.minecraft.launchwrapper.IClassTransformer;

public class WorldObjectTransformer implements IClassTransformer
{
    private static final String WORLD_OBJECT_NAME;
    private static final String RENDER_IN_WORLD_NAME = "renderInWorld";
    private static final String RENDER_IN_WORLD_DESCRIPTOR_OLD = "(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;FFFFZ)V";
    private static final String RENDER_IN_WORLD_DESCRIPTOR_NEW = "(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;DDDFZ)V";
    private final ClassProvider classProvider;
    
    public WorldObjectTransformer() {
        this.classProvider = ClassProvider.getSingleton(name -> Launch.classLoader.loadResource(name));
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] classData) {
        if (classData == null) {
            return null;
        }
        final ClassInfo classInfo = this.classProvider.getOrLoad(name);
        if (this.isInHierarchy(classInfo, WorldObjectTransformer.WORLD_OBJECT_NAME)) {
            final ClassReader reader = new ClassReader(classData);
            final ClassNode classNode = new ClassNode();
            reader.accept((ClassVisitor)classNode, 0);
            for (MethodNode method : classNode.methods) {
                if (method.name.equals("renderInWorld") && method.desc.equals("(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;FFFFZ)V")) {
                    method.desc = "(Lnet/labymod/api/client/world/MinecraftCamera;Lnet/labymod/api/client/render/matrix/Stack;DDDFZ)V";
                    final MethodNode methodNode = method;
                    methodNode.maxLocals += 24;
                    System.out.println("[Compatibility Patcher] WorldObjectPatch was applied to " + name);
                }
            }
            final ClassWriter writer = ASMHelper.newClassWriter();
            classNode.accept((ClassVisitor)writer);
            return writer.toByteArray();
        }
        return classData;
    }
    
    public boolean isInHierarchy(final ClassInfo classInfo, final String name) {
        final ClassInfo superClass = classInfo.getSuperClass();
        if (superClass != null) {
            if (superClass.getName().equals(name)) {
                return true;
            }
            if (this.isInHierarchy(superClass, name)) {
                return true;
            }
        }
        for (final ClassInfo anInterface : classInfo.getInterfaces()) {
            if (anInterface.getName().equals(name)) {
                return true;
            }
            if (this.isInHierarchy(anInterface, name)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        WORLD_OBJECT_NAME = "net.labymod.api.client.world.object.WorldObject".replace('.', '/');
    }
}
