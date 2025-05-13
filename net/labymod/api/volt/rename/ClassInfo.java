// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.rename;

import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import org.objectweb.asm.tree.ClassNode;
import java.util.Map;
import java.util.List;

public class ClassInfo
{
    private final String name;
    private final ClassInfo superClass;
    private final List<ClassInfo> interfaces;
    private final Map<String, FieldInfo> fields;
    private final Map<String, MethodInfo> methods;
    
    private ClassInfo(final String name, final ClassInfo superClass, final List<ClassInfo> interfaces, final Map<String, FieldInfo> fields, final Map<String, MethodInfo> methods) {
        this.name = name;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
    }
    
    public static ClassInfo parse(final ClassProvider provider, final ClassNode node) {
        final String name = node.name;
        final ClassInfo superClass = provider.getOrLoad(node.superName);
        final List<ClassInfo> interfaces = new ArrayList<ClassInfo>();
        for (final String anInterface : node.interfaces) {
            final ClassInfo classInfo = provider.getOrLoad(anInterface);
            if (classInfo == null) {
                continue;
            }
            interfaces.add(classInfo);
        }
        return new ClassInfo(name, superClass, interfaces, parseFields(node), parseMethods(node));
    }
    
    private static Map<String, MethodInfo> parseMethods(final ClassNode node) {
        final Map<String, MethodInfo> mappedMethods = new HashMap<String, MethodInfo>();
        for (MethodNode method : node.methods) {
            mappedMethods.put(method.name + method.desc, new MethodInfo(method.name, method.desc));
        }
        return mappedMethods;
    }
    
    private static Map<String, FieldInfo> parseFields(final ClassNode node) {
        final Map<String, FieldInfo> mappedFields = new HashMap<String, FieldInfo>();
        for (FieldNode field : node.fields) {
            mappedFields.put(field.name + field.desc, new FieldInfo(field.name, field.desc));
        }
        return mappedFields;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ClassInfo getSuperClass() {
        return this.superClass;
    }
    
    public List<ClassInfo> getInterfaces() {
        return this.interfaces;
    }
    
    public Map<String, FieldInfo> getFields() {
        return this.fields;
    }
    
    public Map<String, MethodInfo> getMethods() {
        return this.methods;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
