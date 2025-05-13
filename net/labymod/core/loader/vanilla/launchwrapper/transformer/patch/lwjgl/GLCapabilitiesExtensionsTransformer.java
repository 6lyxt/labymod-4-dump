// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import org.objectweb.asm.tree.FieldNode;
import java.util.ListIterator;
import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import java.io.InputStream;
import java.net.URL;
import net.labymod.core.loader.isolated.util.IsolatedClassLoader;
import java.io.IOException;
import net.labymod.core.loader.isolated.util.IsolatedClassLoaders;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class GLCapabilitiesExtensionsTransformer implements IClassTransformer
{
    private static final String NAME = "org.lwjgl.opengl.GLCapabilities";
    private static final String[] MISSING_EXTENSIONS;
    
    public byte[] transform(final String name, final String transformedName, byte... classData) {
        if (name.equals("org.lwjgl.opengl.GLCapabilities") && classData == null) {
            classData = this.getClassBytes(name);
        }
        if (!name.equals("org.lwjgl.opengl.GLCapabilities") || classData == null) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, this::patch);
    }
    
    private byte[] getClassBytes(final String name) {
        final IsolatedClassLoader lwjglClassLoader = IsolatedClassLoaders.LWJGL_CLASS_LOADER;
        final URL resource = lwjglClassLoader.findResource(name.replace(".", "/").concat(".class"));
        if (resource == null) {
            throw new RuntimeException();
        }
        try (final InputStream stream = resource.openStream()) {
            return stream.readAllBytes();
        }
        catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    private void patch(final ClassNode classNode) {
        for (final String missingExtension : GLCapabilitiesExtensionsTransformer.MISSING_EXTENSIONS) {
            classNode.fields.add(this.createExtensionField(missingExtension));
        }
        for (final MethodNode method : classNode.methods) {
            if (method.name.equals("<init>")) {
                AbstractInsnNode lastReturnInstruction = null;
                for (final AbstractInsnNode instruction : method.instructions) {
                    if (instruction.getOpcode() != 177) {
                        continue;
                    }
                    lastReturnInstruction = instruction;
                }
                if (lastReturnInstruction != null) {
                    break;
                }
                break;
            }
        }
    }
    
    private FieldNode createExtensionField(final String name) {
        return new FieldNode(17, name, "Z", (String)null, (Object)false);
    }
    
    static {
        MISSING_EXTENSIONS = new String[] { "GL_EXT_multi_draw_arrays", "GL_EXT_paletted_texture", "GL_EXT_rescale_normal", "GL_EXT_texture_3d", "GL_EXT_texture_lod_bias", "GL_EXT_vertex_shader", "GL_EXT_vertex_weighting" };
    }
}
