// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import org.objectweb.asm.MethodVisitor;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Paths;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import net.minecraft.launchwrapper.IClassTransformer;

public class TextureDebuggerTransformer implements IClassTransformer
{
    private static final String TEXTURE_DEBUGGER_NAME = "net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.debug.TextureDebugger";
    private static final String GL11_NAME = "org.lwjgl.opengl.GL11";
    private static final String GL13_NAME = "org.lwjgl.opengl.GL13";
    private static final String ARBMULTITEXTURE_NAME = "org.lwjgl.opengl.ARBMultitexture";
    private static final String[] METHODS;
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("org.lwjgl") || name.equals("net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.debug.TextureDebugger")) {
            return classData;
        }
        final ClassReader reader = new ClassReader(classData);
        final ClassWriter writer = new ClassWriter(reader, 1);
        final ClassVisitor visitor = new TextureDebuggerClassVisitor((ClassVisitor)writer);
        reader.accept(visitor, 8);
        final byte[] newClassData = writer.toByteArray();
        if (!Arrays.equals(classData, newClassData)) {
            writeClassData("debug." + name, newClassData);
        }
        return newClassData;
    }
    
    private static void writeClassData(final String name, final byte[] classData) {
        try {
            final Path modifiedClassDataPath = Paths.get("labymod-neo/debug/asm/" + name.replace('.', '/').concat(".class"), new String[0]);
            Files.createDirectories(modifiedClassDataPath.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
            Files.write(modifiedClassDataPath, classData, new OpenOption[0]);
        }
        catch (final IOException ex) {}
    }
    
    public static boolean containsMethod(final String name) {
        for (final String method : TextureDebuggerTransformer.METHODS) {
            if (method.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static String getMethodName(final String name) {
        for (final String method : TextureDebuggerTransformer.METHODS) {
            if (method.equals(name)) {
                return method;
            }
        }
        return null;
    }
    
    private static String getName(final String name) {
        return name.replace('.', '/');
    }
    
    static {
        METHODS = new String[] { "glActiveTexture", "glEnable", "glDisable", "glGenTextures", "glBindTexture", "glDeleteTextures", "glTexImage2D", "glTexParameteri", "glTexParameteriv", "glTexParameterf", "glTexParameterfv", "glCopyTexSubImage2D", "glTexSubImage2D", "glMultiTexCoord2f", "glClientActiveTexture", "glClientActiveTextureARB", "glMultiTexCoord2fARB", "glActiveTextureARB", "glTexGeni", "glTexGeniv", "glTexGenf", "glTexGenfv" };
    }
    
    private static class TextureDebuggerClassVisitor extends ClassVisitor
    {
        protected TextureDebuggerClassVisitor(final ClassVisitor classVisitor) {
            super(589824, classVisitor);
        }
        
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            return new TextureDebuggerMethodVisitor(this.api, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }
    
    private static class TextureDebuggerMethodVisitor extends MethodVisitor
    {
        public TextureDebuggerMethodVisitor(final int api, final MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }
        
        public void visitMethodInsn(final int opcode, String owner, String name, final String descriptor, final boolean isInterface) {
            if ((owner.equals(TextureDebuggerTransformer.getName("org.lwjgl.opengl.GL11")) || owner.equals(TextureDebuggerTransformer.getName("org.lwjgl.opengl.GL13")) || owner.equals(TextureDebuggerTransformer.getName("org.lwjgl.opengl.ARBMultitexture"))) && TextureDebuggerTransformer.containsMethod(name)) {
                owner = "net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.debug.TextureDebugger".replace('.', '/');
                name = TextureDebuggerTransformer.getMethodName(name);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
