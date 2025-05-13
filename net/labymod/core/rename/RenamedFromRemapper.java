// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.rename;

import java.util.Iterator;
import net.labymod.api.mappings.MethodMapping;
import net.labymod.api.mappings.ClassMapping;
import net.labymod.api.volt.rename.ClassInfo;
import net.labymod.api.volt.rename.ClassProvider;
import net.labymod.api.mappings.MappingFile;
import org.objectweb.asm.commons.Remapper;

public class RenamedFromRemapper extends Remapper
{
    private static final String JAVA_PREFIX = "java/";
    private final MappingFile provider;
    private final MappingFile reverseProvider;
    private final ClassProvider classProvider;
    
    public RenamedFromRemapper(final MappingFile provider, final ClassProvider classProvider) {
        this.provider = provider.reverse();
        this.reverseProvider = provider;
        this.classProvider = classProvider;
    }
    
    public String mapMethodName(final String owner, final String name, final String descriptor) {
        final ClassMapping classMapping = this.provider.getClass(owner);
        final MethodMapping methodMapping = (classMapping == null) ? null : classMapping.remapMethod(name, descriptor);
        final String mappedName = (methodMapping == null) ? name : methodMapping.getMapped();
        if (name.equals(mappedName)) {
            final ClassInfo classInfo = this.classProvider.getOrLoad(this.map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            final ClassInfo superClass = classInfo.getSuperClass();
            if (superClass != null && !superClass.getName().startsWith("java/")) {
                final String mappedSuperName = this.mapMethodName(this.unmap(superClass.getName()), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (final ClassInfo interfaceClass : classInfo.getInterfaces()) {
                final String interfaceName = interfaceClass.getName();
                if (interfaceName.startsWith("java/")) {
                    continue;
                }
                final String mappedInterfaceName = this.mapMethodName(this.unmap(interfaceName), name, descriptor);
                if (!mappedInterfaceName.equals(name)) {
                    return mappedInterfaceName;
                }
            }
        }
        return mappedName;
    }
    
    public String mapRecordComponentName(final String owner, final String name, final String descriptor) {
        return this.mapFieldName(owner, name, descriptor);
    }
    
    public String mapFieldName(final String owner, final String name, final String descriptor) {
        final ClassMapping classMapping = this.provider.getClass(owner);
        final String mappedName = (classMapping == null) ? name : classMapping.remapField(name);
        if (name.equals(mappedName)) {
            final ClassInfo classInfo = this.classProvider.getOrLoad(this.map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            final ClassInfo superClass = classInfo.getSuperClass();
            if (superClass != null && !superClass.getName().startsWith("java/")) {
                final String mappedSuperName = this.mapFieldName(this.unmap(superClass.getName()), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (final ClassInfo interfaceClass : classInfo.getInterfaces()) {
                final String interfaceName = interfaceClass.getName();
                if (interfaceName.startsWith("java/")) {
                    continue;
                }
                final String mappedInterfaceName = this.mapFieldName(this.unmap(interfaceName), name, descriptor);
                if (!mappedInterfaceName.equals(name)) {
                    return mappedInterfaceName;
                }
            }
        }
        return mappedName;
    }
    
    public String map(final String internalName) {
        return this.provider.remapClass(internalName);
    }
    
    private String unmap(final String internalName) {
        return this.reverseProvider.remapClass(internalName);
    }
}
