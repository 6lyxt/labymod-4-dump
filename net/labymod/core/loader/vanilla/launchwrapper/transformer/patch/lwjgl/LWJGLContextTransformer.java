// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.ListIterator;
import java.util.Iterator;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;

public class LWJGLContextTransformer implements IClassTransformer
{
    private static final String GL_MATRIX_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix";
    private static final String AL_SOUND_FIXER_CLASS = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/sound/ALSoundFixer";
    private static final String GL_MATRIX_CLASS_REFLECTION;
    private static final Map<String, String> defaultMappings;
    private static final Map<String, String> methodMappings;
    
    public byte[] transform(final String name, final String transformedName, byte... classData) {
        if (classData == null) {
            return null;
        }
        final ClassReader reader = new ClassReader(classData);
        final ClassNode classVisitor = new ClassNode();
        final Remapper remapper = (Remapper)new SimpleRemapper((Map)LWJGLContextTransformer.defaultMappings);
        final ClassRemapper classRemapper = new ClassRemapper((ClassVisitor)classVisitor, remapper);
        reader.accept((ClassVisitor)classRemapper, 0);
        if (!name.equals(LWJGLContextTransformer.GL_MATRIX_CLASS_REFLECTION)) {
            this.trackMatrix(classVisitor);
        }
        this.fixAL(classVisitor);
        final ClassWriter classWriter = ASMHelper.newClassWriter(reader, false);
        classVisitor.accept((ClassVisitor)classWriter);
        classData = classWriter.toByteArray();
        return classData;
    }
    
    private void trackMatrix(final ClassNode node) {
        this.renameMethodInvokeCall(node, 184, "org/lwjgl/opengl/GL11");
    }
    
    private void fixAL(final ClassNode node) {
        this.renameMethodInvokeCall(node, 184, "org/lwjgl/openal/AL");
    }
    
    private void renameMethodInvokeCall(final ClassNode node, final int opcode, final String owner) {
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction.getOpcode() != opcode) {
                    continue;
                }
                if (!(instruction instanceof MethodInsnNode)) {
                    continue;
                }
                final MethodInsnNode methodInstruction = (MethodInsnNode)instruction;
                if (!methodInstruction.owner.equals(owner)) {
                    continue;
                }
                final String methodCall = methodInstruction.owner + "." + methodInstruction.name + methodInstruction.desc;
                final String newMethodCall = LWJGLContextTransformer.methodMappings.get(methodCall);
                if (newMethodCall == null) {
                    continue;
                }
                final int dotIndex = newMethodCall.indexOf(".");
                methodInstruction.owner = newMethodCall.substring(0, dotIndex);
                final int endIndex = newMethodCall.indexOf("(");
                methodInstruction.name = newMethodCall.substring(dotIndex + 1, endIndex);
                methodInstruction.desc = newMethodCall.substring(endIndex);
            }
        }
    }
    
    private static <T> T make(final T t, final Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }
    
    static {
        GL_MATRIX_CLASS_REFLECTION = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix".replace("/", ".");
        defaultMappings = make((HashMap)new HashMap(), map -> {
            map.put("org/lwjgl/opengl/GLContext", "org/lwjgl/opengl/GL");
            map.put("org/lwjgl/opengl/ContextCapabilities", "org/lwjgl/opengl/GLCapabilities");
            map.put("org/lwjgl/opengl/GL11.glGetFloat(ILjava/nio/FloatBuffer;)V", "glGetFloatv");
            map.put("org/lwjgl/opengl/GL11.glGetInteger(ILjava/nio/IntBuffer;)V", "glGetIntegerv");
            map.put("org/lwjgl/opengl/GL11.glFog(ILjava/nio/FloatBuffer;)V", "glFogfv");
            map.put("org/lwjgl/opengl/GL11.glMultMatrix(Ljava/nio/FloatBuffer;)V", "glMultMatrixf");
            map.put("org/lwjgl/opengl/GL11.glLight(IILjava/nio/FloatBuffer;)V", "glLightfv");
            map.put("org/lwjgl/opengl/GL11.glLightModel(ILjava/nio/FloatBuffer;)V", "glLightModelfv");
            map.put("org/lwjgl/opengl/GL11.glTexEnv(IILjava/nio/FloatBuffer;)V", "glTexEnvfv");
            map.put("org/lwjgl/opengl/GL11.glTexGen(IILjava/nio/FloatBuffer;)V", "glTexGenfv");
            map.put("org/lwjgl/opengl/GL11.glLoadMatrix(Ljava/nio/FloatBuffer;)V", "glLoadMatrixf");
            map.put("org/lwjgl/openal/AL10.alListener(ILjava/nio/FloatBuffer;)V", "alListenerfv");
            map.put("org/lwjgl/openal/AL10.alSource(IILjava/nio/FloatBuffer;)V", "alSourcefv");
            map.put("org/lwjgl/openal/AL10.alSourceStop(Ljava/nio/IntBuffer;)V", "alSourceStopv");
            map.put("org/lwjgl/opengl/ARBShaderObjects.glGetObjectParameterARB(IILjava/nio/IntBuffer;)V", "glGetObjectParameterivARB");
            map.put("org/lwjgl/opengl/ARBShaderObjects.glUniformMatrix2ARB(IZLjava/nio/FloatBuffer;)V", "glUniformMatrix2fvARB");
            map.put("org/lwjgl/opengl/ARBShaderObjects.glUniformMatrix3ARB(IZLjava/nio/FloatBuffer;)V", "glUniformMatrix3fvARB");
            map.put("org/lwjgl/opengl/ARBShaderObjects.glUniformMatrix4ARB(IZLjava/nio/FloatBuffer;)V", "glUniformMatrix4fvARB");
            return;
        });
        methodMappings = make((HashMap)new HashMap(), map -> {
            map.put("org/lwjgl/opengl/GL11.glPushMatrix()V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glPushMatrix()V");
            map.put("org/lwjgl/opengl/GL11.glPopMatrix()V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glPopMatrix()V");
            map.put("org/lwjgl/opengl/GL11.glTranslatef(FFF)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glTranslatef(FFF)V");
            map.put("org/lwjgl/opengl/GL11.glTranslated(DDD)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glTranslated(DDD)V");
            map.put("org/lwjgl/opengl/GL11.glScalef(FFF)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glScalef(FFF)V");
            map.put("org/lwjgl/opengl/GL11.glScaled(DDD)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glScaled(DDD)V");
            map.put("org/lwjgl/opengl/GL11.glRotatef(FFFF)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glRotatef(FFFF)V");
            map.put("org/lwjgl/opengl/GL11.glRotated(DDDD)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glRotated(DDDD)V");
            map.put("org/lwjgl/opengl/GL11.glLoadIdentity()V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glLoadIdentity()V");
            map.put("org/lwjgl/opengl/GL11.glMultMatrixf(Ljava/nio/FloatBuffer;)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glMultMatrixf(Ljava/nio/FloatBuffer;)V");
            map.put("org/lwjgl/opengl/GL11.glOrtho(DDDDDD)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glOrtho(DDDDDD)V");
            map.put("org/lwjgl/opengl/GL11.glMatrixMode(I)V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLMatrix.glMatrixMode(I)V");
            map.put("org/lwjgl/openal/AL.create()V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/sound/ALSoundFixer.create()V");
            map.put("org/lwjgl/openal/AL.destroy()V", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/sound/ALSoundFixer.destroy()V");
        });
    }
}
