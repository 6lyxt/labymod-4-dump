// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.data;

import java.util.Iterator;
import net.labymod.api.volt.asm.VoltClassWriter;
import java.io.InputStream;
import java.io.IOException;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassReader;
import java.net.URL;
import java.util.Collection;
import java.util.ArrayList;
import org.objectweb.asm.tree.ClassNode;
import java.util.List;

public class ClassData
{
    private final String name;
    private final String superName;
    private final boolean isInterface;
    private final List<ClassData> targets;
    private ClassData superClassData;
    private final List<String> interfaces;
    
    public ClassData(final ClassNode node) {
        this.targets = new ArrayList<ClassData>();
        this.interfaces = new ArrayList<String>();
        this.name = node.name;
        this.superName = ((node.superName != null) ? node.superName : null);
        this.interfaces.addAll(node.interfaces);
        this.isInterface = ((node.access & 0x200) != 0x0);
        this.targets.add(this);
    }
    
    public static ClassData forResource(final URL resourceUrl) {
        try (final InputStream stream = resourceUrl.openStream()) {
            final ClassReader reader = new ClassReader(stream.readAllBytes());
            final ClassNode classNode = new ClassNode();
            reader.accept((ClassVisitor)classNode, 0);
            return new ClassData(classNode);
        }
        catch (final IOException exception) {
            return null;
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSuperName() {
        return this.superName;
    }
    
    public List<String> getInterfaces() {
        return this.interfaces;
    }
    
    public ClassData getSuperClassData() {
        if (this.superClassData == null && this.superName != null) {
            this.superClassData = VoltClassWriter.getClassData(this.superName);
        }
        return this.superClassData;
    }
    
    public boolean hasSuperClass(final ClassData other) {
        return this.hasSuperclass(other, false);
    }
    
    public boolean isInterface() {
        return this.isInterface;
    }
    
    public List<ClassData> getTargets() {
        return this.targets;
    }
    
    private boolean hasSuperclass(final ClassData other, final boolean includeInterfaces) {
        return "java/lang/Object".equals(other.name) || this.findSuperclass(other.name, includeInterfaces) != null;
    }
    
    private ClassData findSuperclass(final String name, final boolean includeInterfaces) {
        if ("java/lang/Object".equals(name)) {
            return null;
        }
        final ClassData superClassData = this.getSuperClassData();
        if (superClassData != null) {
            for (final ClassData target : superClassData.getTargets()) {
                if (name.equals(target.getName())) {
                    return superClassData;
                }
                final ClassData found = target.findSuperclass(name, includeInterfaces);
                if (found != null) {
                    return found;
                }
            }
        }
        if (includeInterfaces) {
            return this.findInterface(name);
        }
        return null;
    }
    
    private ClassData findInterface(final String superClass) {
        for (final String interfaceName : this.getInterfaces()) {
            final ClassData interfaceClassData = VoltClassWriter.getClassData(interfaceName);
            if (superClass.equals(interfaceName)) {
                return interfaceClassData;
            }
            if (interfaceClassData == null) {
                continue;
            }
            final ClassData superInterface = interfaceClassData.findInterface(superClass);
            if (superInterface != null) {
                return superInterface;
            }
        }
        return null;
    }
    
    public static String findCommonSuperClass(final ClassData type1, final ClassData type2) {
        return findCommonSuperClass(type1, type2, false);
    }
    
    public static String findCommonSuperClass(ClassData type1, final ClassData type2, final boolean includeInterfaces) {
        if (type1 == null || type2 == null) {
            return "java/lang/Object";
        }
        if (type1.hasSuperclass(type2, includeInterfaces)) {
            return type2.getName();
        }
        if (type2.hasSuperclass(type1, includeInterfaces)) {
            return type1.getName();
        }
        if (type1.isInterface() || type2.isInterface()) {
            return "java/lang/Object";
        }
        do {
            type1 = type1.getSuperClassData();
            if (type1 == null) {
                return "java/lang/Object";
            }
        } while (!type2.hasSuperclass(type1, includeInterfaces));
        return type1.getName();
    }
}
