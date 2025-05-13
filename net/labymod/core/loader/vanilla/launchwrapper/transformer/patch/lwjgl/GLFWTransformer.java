// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer.patch.lwjgl;

import net.labymod.api.util.function.Functional;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Iterator;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassWriter;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;

public class GLFWTransformer implements IClassTransformer
{
    private static final boolean DEBUG;
    private static final String GLFW_NAME = "org/lwjgl/glfw/GLFW";
    private static final String GLFW_REDIRECTOR_NAME = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector";
    private static final String GLFW_REDIRECTOR_NAME_CANONICAL;
    private static final Map<String, String> MAPPINGS;
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        if (GLFWTransformer.GLFW_REDIRECTOR_NAME_CANONICAL.equals(name)) {
            return classData;
        }
        final ClassReader reader = new ClassReader(classData);
        final ClassNode node = new ClassNode();
        reader.accept((ClassVisitor)node, 0);
        final boolean patched = this.patchGLFWMethods(node);
        final ClassWriter classWriter = ASMHelper.newClassWriter(reader, false);
        node.accept((ClassVisitor)classWriter);
        final byte[] newClassData = classWriter.toByteArray();
        if (GLFWTransformer.DEBUG && patched) {
            System.out.println("Transformed GLFW methods in " + name);
            ASMHelper.writeClassData(name, newClassData, true);
        }
        return newClassData;
    }
    
    private boolean patchGLFWMethods(final ClassNode node) {
        boolean transformed = false;
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction.getOpcode() != 184) {
                    continue;
                }
                if (!(instruction instanceof MethodInsnNode)) {
                    continue;
                }
                final MethodInsnNode methodInstruction = (MethodInsnNode)instruction;
                if (!methodInstruction.owner.equals("org/lwjgl/glfw/GLFW")) {
                    continue;
                }
                final String methodClass = methodInstruction.owner + "." + methodInstruction.name + methodInstruction.desc;
                final String newMethodClass = GLFWTransformer.MAPPINGS.get(methodClass);
                if (newMethodClass == null) {
                    continue;
                }
                final int dotIndex = newMethodClass.lastIndexOf(46);
                methodInstruction.owner = newMethodClass.substring(0, dotIndex);
                final int methodNameEndIndex = newMethodClass.indexOf(40);
                methodInstruction.name = newMethodClass.substring(dotIndex + 1, methodNameEndIndex);
                methodInstruction.desc = newMethodClass.substring(methodNameEndIndex);
                transformed = true;
            }
        }
        return transformed;
    }
    
    private static void registerMappings(final Map<String, String> mappings, final String oldName, final String newName, final String method) {
        mappings.put(oldName + "." + method, newName + "." + method);
    }
    
    static {
        DEBUG = Boolean.getBoolean("net.labymod.debugging.asm");
        GLFW_REDIRECTOR_NAME_CANONICAL = "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector".replace('/', '.');
        MAPPINGS = Functional.of((HashMap)new HashMap(), mappings -> {
            registerMappings(mappings, "org/lwjgl/glfw/GLFW", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector", "nglfwCreateWindow(IIJJJ)J");
            registerMappings(mappings, "org/lwjgl/glfw/GLFW", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector", "glfwShowWindow(J)V");
            registerMappings(mappings, "org/lwjgl/glfw/GLFW", "net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GLFWRedirector", "glfwDestroyWindow(J)V");
        });
    }
}
