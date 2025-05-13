// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.util;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.tree.AnnotationNode;
import java.util.function.Predicate;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.Type;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.volt.asm.VoltClassWriter;
import org.objectweb.asm.tree.MethodNode;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Paths;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import java.util.function.Consumer;

public class ASMHelper
{
    public static final int ASM_VERSION = 589824;
    
    public static byte[] transformClassData(final byte[] classData, final Consumer<ClassNode> callback) {
        return transformClassData(classData, callback, true);
    }
    
    public static byte[] transformClassData(final byte[] classData, final Consumer<ClassNode> callback, final boolean computeFrames) {
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(classData);
        classReader.accept((ClassVisitor)classNode, 0);
        if (callback != null) {
            callback.accept(classNode);
        }
        final ClassWriter classWriter = newClassWriter(classReader, computeFrames);
        classNode.accept((ClassVisitor)classWriter);
        final byte[] modifiedClassData = classWriter.toByteArray();
        writeClassData(classNode.name, modifiedClassData);
        return modifiedClassData;
    }
    
    public static void writeClassData(final String name, final byte[] classData) {
        writeClassData(name, classData, Boolean.getBoolean("net.labymod.debugging.asm"));
    }
    
    public static void writeClassData(final String name, final byte[] classData, final boolean allowedToWrite) {
        if (!allowedToWrite) {
            return;
        }
        try {
            final String classPath = name.replace('.', '/').concat(".class");
            final Path modifiedClassDataPath = Paths.get("labymod-neo/debug/asm/" + classPath, new String[0]);
            Files.createDirectories(modifiedClassDataPath.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
            Files.write(modifiedClassDataPath, classData, new OpenOption[0]);
        }
        catch (final IOException ex) {}
    }
    
    public static ClassNode getClassNode(final String className) throws IOException {
        final ClassNode classNode = new ClassNode();
        final ClassReader reader = new ClassReader(className);
        reader.accept((ClassVisitor)classNode, 0);
        return classNode;
    }
    
    public static ClassNode getClassNode(final InputStream stream) throws IOException {
        final ClassNode classNode = new ClassNode();
        final ClassReader reader = new ClassReader(stream);
        reader.accept((ClassVisitor)classNode, 0);
        return classNode;
    }
    
    public static MethodNode createStaticConstructor() {
        return new MethodNode(8, "<clinit>", "()V", (String)null, (String[])null);
    }
    
    public static MethodNode createPublicConstructor() {
        return new MethodNode(1, "<init>", "()V", (String)null, (String[])null);
    }
    
    public static ClassWriter newClassWriter() {
        return newClassWriter(writer -> writer.setClassLoader(ASMContext.getPlatformClassLoader()));
    }
    
    public static ClassWriter newClassWriter(final Consumer<VoltClassWriter> writerConsumer) {
        final VoltClassWriter writer = new VoltClassWriter(3);
        if (writerConsumer != null) {
            writerConsumer.accept(writer);
        }
        return writer;
    }
    
    public static ClassWriter newClassWriter(final ClassReader reader) {
        return newClassWriter(reader, true);
    }
    
    public static ClassWriter newClassWriter(final ClassReader reader, final Consumer<VoltClassWriter> writerConsumer) {
        return newClassWriter(reader, writerConsumer, true);
    }
    
    public static ClassWriter newClassWriter(final ClassReader reader, final boolean computeFrames) {
        return newClassWriter(reader, writer -> writer.setClassLoader(ASMContext.getPlatformClassLoader()), computeFrames);
    }
    
    public static ClassWriter newClassWriter(final ClassReader reader, final Consumer<VoltClassWriter> writerConsumer, final boolean computeFrames) {
        final VoltClassWriter writer = new VoltClassWriter(reader, computeFrames ? 3 : 0);
        if (writerConsumer != null) {
            writerConsumer.accept(writer);
        }
        return writer;
    }
    
    public static boolean canLoadClass(final int classVersion) {
        return canLoadClass(classVersion, getCurrentJavaClassVersion());
    }
    
    public static boolean canLoadClass(final int classVersion, final int javaVersion) {
        return classVersion <= javaVersion;
    }
    
    public static int getCurrentJavaClassVersion() {
        try {
            final String property = System.getProperty("java.class.version");
            return (int)Double.parseDouble(property);
        }
        catch (final NumberFormatException exception) {
            return -1;
        }
    }
    
    @NotNull
    public static String getMethodDescriptor(@NotNull final MethodNode node) {
        return node.name + node.desc;
    }
    
    public static MethodNode createMethod(final int access, final String name, final String descriptor, final String signature, final Collection<String> exceptions, final Consumer<MethodNode> callback) {
        final MethodNode method = new MethodNode(access, name, descriptor, signature, (String[])((exceptions == null) ? null : ((String[])exceptions.toArray(new String[0]))));
        if (callback != null) {
            callback.accept(method);
        }
        return method;
    }
    
    public static Type getLastParameter(final String methodDescriptor) {
        final Type[] types = Type.getArgumentTypes(methodDescriptor);
        return (types.length == 0) ? null : types[types.length - 1];
    }
    
    public static String getNameWithDesc(final MethodNode node) {
        return node.name + node.desc;
    }
    
    public static AbstractInsnNode findInstruction(AbstractInsnNode node, final boolean next, final Predicate<AbstractInsnNode> filter) {
        if (node == null) {
            return null;
        }
        if (filter.test(node)) {
            return node;
        }
        node = (next ? node.getNext() : node.getPrevious());
        return findInstruction(node, next, filter);
    }
    
    public static Map<String, Object> getAnnotationValues(final AnnotationNode node) {
        if (node == null) {
            return new HashMap<String, Object>();
        }
        final List<Object> values = node.values;
        if (values == null) {
            return new HashMap<String, Object>();
        }
        final Map<String, Object> valueMap = new HashMap<String, Object>();
        for (int size = values.size(), index = 0; index < size; index += 2) {
            final Object key = values.get(index);
            final Object value = values.get(index + 1);
            valueMap.put(String.valueOf(key), value);
        }
        return valueMap;
    }
}
