// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import org.objectweb.asm.MethodVisitor;
import net.labymod.api.util.function.Functional;
import java.util.HashMap;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;

public class OpenGLTransformer implements IClassTransformer
{
    private static final String GL11_ORIGINAL_CLASS = "org/lwjgl/opengl/GL11";
    private static final String DRAW_CALL_REPLACED_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/DrawCallTracker";
    private static final String DRAW_CALL_REPLACED_CLASS_REFLECTION;
    private static final String GL30_ORIGINAL_CLASS = "org/lwjgl/opengl/GL30";
    private static final String VAO_REPLACED_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/VertexArrayObjectTracker";
    private static final String VAO_REPLACED_CLASS_REFLECTION;
    private static final Map<String, String> METHOD_MAPPINGS;
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        if (name.startsWith("org/lwjgl/") || name.equals(OpenGLTransformer.DRAW_CALL_REPLACED_CLASS_REFLECTION) || name.equals(OpenGLTransformer.VAO_REPLACED_CLASS_REFLECTION)) {
            return classData;
        }
        final ClassReader reader = new ClassReader(classData);
        final ClassWriter writer = new ClassWriter(reader, 1);
        final ClassVisitor visitor = new TransformerClassVisitor((ClassVisitor)writer);
        reader.accept(visitor, 8);
        return writer.toByteArray();
    }
    
    static {
        DRAW_CALL_REPLACED_CLASS_REFLECTION = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/DrawCallTracker".replace('/', '.');
        VAO_REPLACED_CLASS_REFLECTION = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/VertexArrayObjectTracker".replace('/', '.');
        METHOD_MAPPINGS = Functional.of((HashMap)new HashMap(), map -> {
            map.put("glBindVertexArray", "glBindVertexArray");
            map.put("glGenVertexArrays", "glGenVertexArrays");
            map.put("glDeleteVertexArrays", "glDeleteVertexArrays");
            map.put("glDrawElements", "glDrawElements");
        });
    }
    
    private static class TransformerClassVisitor extends ClassVisitor
    {
        protected TransformerClassVisitor(final ClassVisitor classVisitor) {
            super(589824, classVisitor);
        }
        
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            return new TrackerMethodVisitor(this.api, super.visitMethod(access, name, descriptor, signature, exceptions));
        }
    }
    
    private static class TrackerMethodVisitor extends MethodVisitor
    {
        protected TrackerMethodVisitor(final int api, final MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }
        
        public void visitMethodInsn(final int opcode, String owner, String name, final String descriptor, final boolean isInterface) {
            if (owner.equals("org/lwjgl/opengl/GL30") && OpenGLTransformer.METHOD_MAPPINGS.containsKey(name)) {
                owner = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/VertexArrayObjectTracker";
                name = OpenGLTransformer.METHOD_MAPPINGS.get(name);
            }
            if (owner.equals("org/lwjgl/opengl/GL11") && OpenGLTransformer.METHOD_MAPPINGS.containsKey(name)) {
                owner = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/opengl/DrawCallTracker";
                name = OpenGLTransformer.METHOD_MAPPINGS.get(name);
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
