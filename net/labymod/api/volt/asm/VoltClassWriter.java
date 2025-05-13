// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm;

import java.util.HashMap;
import net.labymod.api.volt.asm.util.ASMContext;
import java.net.URL;
import org.objectweb.asm.ClassReader;
import net.labymod.api.volt.data.ClassData;
import java.util.Map;
import org.objectweb.asm.ClassWriter;

public class VoltClassWriter extends ClassWriter
{
    private static final Map<String, ClassData> CLASS_DATA_CACHE;
    private static ClassLoader classLoader;
    
    public VoltClassWriter(final int flags) {
        super(flags);
    }
    
    public VoltClassWriter(final ClassReader classReader, final int flags) {
        super(classReader, flags);
    }
    
    protected String getCommonSuperClass(final String type1, final String type2) {
        if (type1 == null || type2 == null) {
            return "java/lang/Object";
        }
        return ClassData.findCommonSuperClass(getClassData(type1), getClassData(type2));
    }
    
    public ClassLoader getClassLoader() {
        return (VoltClassWriter.classLoader == null) ? super.getClassLoader() : VoltClassWriter.classLoader;
    }
    
    public void setClassLoader(final ClassLoader classLoader) {
        VoltClassWriter.classLoader = classLoader;
    }
    
    public static ClassData getClassData(final String name) {
        ClassData classData = VoltClassWriter.CLASS_DATA_CACHE.get(name);
        if (classData != null) {
            return classData;
        }
        final String resourcePath = name.replace('.', '/').concat(".class");
        final URL resource = findResource(resourcePath);
        if (resource == null) {
            return null;
        }
        classData = ClassData.forResource(resource);
        VoltClassWriter.CLASS_DATA_CACHE.put(name, classData);
        return classData;
    }
    
    private static URL findResource(final String path) {
        return ASMContext.findResource(path);
    }
    
    static {
        CLASS_DATA_CACHE = new HashMap<String, ClassData>(1000);
    }
}
