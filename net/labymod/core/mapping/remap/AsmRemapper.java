// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.remap;

import java.util.Iterator;
import net.labymod.api.mapping.provider.child.ClassMapping;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import net.labymod.api.mapping.provider.MappingProvider;
import org.objectweb.asm.commons.Remapper;

public class AsmRemapper extends Remapper
{
    private static final String JAVA_PREFIX = "java/";
    private final MappingProvider provider;
    private final MappingProvider reverseProvider;
    
    public AsmRemapper(final MappingProvider provider) {
        this.provider = provider;
        this.reverseProvider = provider.reverse();
    }
    
    public String mapMethodName(final String owner, final String name, final String descriptor) {
        final ClassMapping classMapping = this.provider.getClassMapping(owner);
        final String mappedName = (classMapping == null) ? name : classMapping.mapMethod(name, descriptor);
        if (name.equals(mappedName)) {
            final ClassInfo classInfo = ClassInfo.forName(this.map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            final String superName = classInfo.getSuperName();
            if (superName != null && !superName.startsWith("java/")) {
                final String mappedSuperName = this.mapMethodName(this.unmap(superName), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (final String interfaceName : classInfo.getInterfaces()) {
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
        final ClassMapping classMapping = this.provider.getClassMapping(owner);
        final String mappedName = (classMapping == null) ? name : classMapping.mapField(name);
        if (name.equals(mappedName)) {
            final ClassInfo classInfo = ClassInfo.forName(this.map(owner));
            if (classInfo == null) {
                return mappedName;
            }
            final String superName = classInfo.getSuperName();
            if (superName != null && !superName.startsWith("java/")) {
                final String mappedSuperName = this.mapFieldName(this.unmap(superName), name, descriptor);
                if (!mappedSuperName.equals(name)) {
                    return mappedSuperName;
                }
            }
            for (final String interfaceName : classInfo.getInterfaces()) {
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
        return this.provider.mapClass(internalName);
    }
    
    private String unmap(final String internalName) {
        return this.reverseProvider.mapClass(internalName);
    }
}
