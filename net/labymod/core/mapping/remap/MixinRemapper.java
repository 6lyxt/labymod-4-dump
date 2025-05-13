// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.remap;

import org.spongepowered.asm.mixin.transformer.ClassInfo;
import java.util.Iterator;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.labymod.api.mapping.provider.child.ClassMapping;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mapping.provider.MappingProvider;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class MixinRemapper implements IRemapper
{
    private static final String JAVA_PREFIX = "java/";
    private final MappingProvider provider;
    private final MappingProvider reverseProvider;
    private final Map<String, IRemapper.MappedMethod> methodNameMap;
    private final Map<String, IRemapper.MappedMethod> methodNameDescriptorMap;
    private final Map<String, IRemapper.MappedMethod> methodClassNameMap;
    private final Map<String, String> fieldNameMap;
    private final Map<String, String> fieldNameDescriptorMap;
    
    public MixinRemapper(final MappingProvider provider) {
        this.provider = provider;
        this.reverseProvider = provider.reverse();
        this.methodNameMap = new HashMap<String, IRemapper.MappedMethod>();
        this.methodNameDescriptorMap = new HashMap<String, IRemapper.MappedMethod>();
        this.methodClassNameMap = new HashMap<String, IRemapper.MappedMethod>();
        this.fieldNameMap = new HashMap<String, String>();
        this.fieldNameDescriptorMap = new HashMap<String, String>();
        for (ClassMapping classMapping : this.provider.getClassMappings()) {
            for (MethodMapping methodMapping : classMapping.getMethodMappings()) {
                final String name = methodMapping.getName();
                final IRemapper.MappedMethod mappedMethod = new IRemapper.MappedMethod(methodMapping.getMappedName(), methodMapping.getMappedDescriptor());
                this.methodNameMap.putIfAbsent(name, mappedMethod);
                this.methodNameDescriptorMap.putIfAbsent(name + methodMapping.getDescriptor(), mappedMethod);
                this.methodClassNameMap.putIfAbsent(classMapping.getName() + name, mappedMethod);
            }
            for (FieldMapping fieldMapping : classMapping.getFieldMappings()) {
                final String name = fieldMapping.getName();
                final String mappedName = fieldMapping.getMappedName();
                this.fieldNameMap.putIfAbsent(name, mappedName);
                this.fieldNameDescriptorMap.putIfAbsent(name + fieldMapping.getDescriptor(), mappedName);
            }
        }
    }
    
    public IRemapper.MappedMethod mapMethod(final String owner, final String name, final String desc) {
        final IRemapper.MappedMethod notMapped = new IRemapper.MappedMethod(name, desc);
        final String unmappedDesc = (desc == null) ? null : this.unmapDesc(desc);
        if (owner != null) {
            final String unmappedOwner = this.unmap(owner);
            final ClassMapping classMapping = this.provider.getClassMapping(unmappedOwner);
            IRemapper.MappedMethod mappedMethod;
            if (unmappedDesc == null || classMapping == null) {
                mappedMethod = this.methodClassNameMap.getOrDefault(unmappedOwner + name, notMapped);
            }
            else {
                final MethodMapping methodMapping = classMapping.getMethodMapping(name, unmappedDesc);
                mappedMethod = ((methodMapping == null) ? notMapped : new IRemapper.MappedMethod(methodMapping.getMappedName(), methodMapping.getMappedDescriptor()));
            }
            if (mappedMethod.getName().equals(name)) {
                final ClassInfo classInfo = ClassInfo.forName(this.map(owner));
                if (classInfo == null) {
                    return mappedMethod;
                }
                final String superName = classInfo.getSuperName();
                if (superName != null && !superName.startsWith("java/")) {
                    final IRemapper.MappedMethod mappedSuperMethod = this.mapMethod(this.unmap(superName), name, desc);
                    if (!mappedSuperMethod.getName().equals(name)) {
                        return mappedSuperMethod;
                    }
                }
                for (final String interfaceName : classInfo.getInterfaces()) {
                    if (interfaceName.startsWith("java/")) {
                        continue;
                    }
                    final IRemapper.MappedMethod mappedInterfaceMethod = this.mapMethod(this.unmap(interfaceName), name, desc);
                    if (!mappedInterfaceMethod.getName().equals(name)) {
                        return mappedInterfaceMethod;
                    }
                }
            }
            return mappedMethod;
        }
        return (unmappedDesc == null) ? this.methodNameMap.getOrDefault(name, notMapped) : this.methodNameDescriptorMap.getOrDefault(name + unmappedDesc, notMapped);
    }
    
    public String mapMethodName(final String owner, final String name, final String desc) {
        return this.mapMethod(owner, name, desc).getName();
    }
    
    public String mapFieldName(final String owner, final String name, final String desc) {
        final String unmappedDesc = (desc == null) ? null : this.unmapDesc(desc);
        if (owner != null) {
            final ClassMapping classMapping = this.provider.getClassMapping(this.unmap(owner));
            final String mappedName = (classMapping == null) ? name : classMapping.mapField(name);
            if (mappedName.equals(name)) {
                final ClassInfo classInfo = ClassInfo.forName(this.map(owner));
                if (classInfo == null) {
                    return mappedName;
                }
                final String superName = classInfo.getSuperName();
                if (superName != null && !superName.startsWith("java/")) {
                    final String mappedSuperName = this.mapFieldName(this.unmap(superName), name, desc);
                    if (!mappedSuperName.equals(name)) {
                        return mappedSuperName;
                    }
                }
                for (final String interfaceName : classInfo.getInterfaces()) {
                    if (interfaceName.startsWith("java/")) {
                        continue;
                    }
                    final String mappedInterfaceName = this.mapFieldName(this.unmap(interfaceName), name, desc);
                    if (!mappedInterfaceName.equals(name)) {
                        return mappedInterfaceName;
                    }
                }
            }
            return mappedName;
        }
        return (unmappedDesc == null) ? this.fieldNameMap.getOrDefault(name, name) : this.fieldNameDescriptorMap.getOrDefault(name + unmappedDesc, name);
    }
    
    public String map(final String typeName) {
        return this.provider.mapClass(typeName);
    }
    
    public String unmap(final String typeName) {
        return this.reverseProvider.mapClass(typeName);
    }
    
    public String mapDesc(final String desc) {
        return this.provider.mapDescriptor(desc);
    }
    
    public String unmapDesc(final String desc) {
        return this.reverseProvider.mapDescriptor(desc);
    }
}
