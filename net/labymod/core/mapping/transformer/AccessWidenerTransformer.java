// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.transformer;

import net.labymod.api.mapping.provider.child.FieldMapping;
import org.objectweb.asm.Type;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.labymod.accesswidener.AccessSpecifier;
import net.labymod.api.mapping.provider.child.ClassMapping;
import java.util.Iterator;
import java.util.Map;
import net.labymod.accesswidener.access.Access;
import java.util.HashMap;
import java.io.IOException;
import net.labymod.accesswidener.AccessWidenerWriter;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import net.labymod.accesswidener.AccessWidenerReader;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.api.mapping.provider.MappingProvider;
import net.minecraftforge.fart.api.Transformer;

public class AccessWidenerTransformer implements Transformer
{
    private final MappingProvider provider;
    
    public AccessWidenerTransformer(final MappingProvider provider) {
        this.provider = provider;
    }
    
    public Transformer.ResourceEntry process(final Transformer.ResourceEntry entry) {
        if (!entry.getName().endsWith(".accesswidener")) {
            return entry;
        }
        return this.remapAccessWidener(entry);
    }
    
    private Transformer.ResourceEntry remapAccessWidener(final Transformer.ResourceEntry entry) {
        final AccessWidener accessWidener = new AccessWidener();
        accessWidener.setNamespace("vanilla");
        final AccessWidenerReader reader = new AccessWidenerReader(accessWidener);
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(entry.getData())) {
            reader.read((InputStream)inputStream);
            final AccessWidener copiedWidener = accessWidener.copy();
            copiedWidener.setNamespace("named");
            this.remapClasses(copiedWidener);
            this.remapMethods(copiedWidener);
            this.remapFields(copiedWidener);
            final AccessWidenerWriter writer = new AccessWidenerWriter(copiedWidener);
            return Transformer.ResourceEntry.create(entry.getName(), entry.getTime(), writer.write());
        }
        catch (final IOException exception) {
            return entry;
        }
    }
    
    private void remapClasses(final AccessWidener widener) {
        final Map<String, Access> classes = new HashMap<String, Access>();
        for (final Map.Entry<String, Access> accessEntry : widener.getClassAccess().entrySet()) {
            final String key = accessEntry.getKey();
            final ClassMapping fileClass = this.provider.getClassMapping(key);
            if (fileClass == null) {
                continue;
            }
            classes.put(fileClass.getMappedName(), accessEntry.getValue());
        }
        widener.getClassAccess().clear();
        widener.getClassAccess().putAll(classes);
    }
    
    private void remapMethods(final AccessWidener widener) {
        final Map<AccessSpecifier, Access> methods = new HashMap<AccessSpecifier, Access>();
        final Map<AccessSpecifier, Access> accesses = widener.getMethodAccess().getAccesses();
        for (final Map.Entry<AccessSpecifier, Access> entry : accesses.entrySet()) {
            final AccessSpecifier specifier = entry.getKey();
            final String owner = specifier.getOwner();
            final ClassMapping mappingClass = this.provider.getClassMapping(owner);
            if (mappingClass == null) {
                continue;
            }
            if (specifier.isWildcard()) {
                methods.put(AccessSpecifier.ofWildcard(mappingClass.getMappedName()), entry.getValue());
            }
            else {
                final String name = specifier.getName();
                final String descriptor = specifier.getDescriptor();
                final MethodMapping method = mappingClass.getMethodMapping(name, descriptor);
                if (method == null) {
                    continue;
                }
                methods.put(AccessSpecifier.of(mappingClass.getMappedName(), method.getMappedName(), method.getMappedDescriptor()), entry.getValue());
            }
        }
        accesses.clear();
        accesses.putAll(methods);
    }
    
    private void remapFields(final AccessWidener widener) {
        final Map<AccessSpecifier, Access> fields = new HashMap<AccessSpecifier, Access>();
        final Map<AccessSpecifier, Access> accesses = widener.getFieldAccess().getAccesses();
        for (Map.Entry<AccessSpecifier, Access> entry : accesses.entrySet()) {
            final AccessSpecifier specifier = entry.getKey();
            final String owner = specifier.getOwner();
            final ClassMapping mappingClass = this.provider.getClassMapping(owner);
            if (mappingClass == null) {
                continue;
            }
            if (specifier.isWildcard()) {
                fields.put(AccessSpecifier.ofWildcard(mappingClass.getMappedName()), entry.getValue());
            }
            else {
                final String name = specifier.getName();
                final FieldMapping field = mappingClass.getFieldMapping(name);
                if (field == null) {
                    continue;
                }
                String descriptor = specifier.getDescriptor();
                if (descriptor != null && descriptor.endsWith(";")) {
                    final Type type = Type.getType(descriptor);
                    if (type != null) {
                        final boolean isArray = type.getSort() == 9;
                        descriptor = (isArray ? type.getElementType().getInternalName() : type.getInternalName());
                        final ClassMapping cls = this.provider.getClassMapping(descriptor);
                        if (cls != null) {
                            descriptor = "L" + cls.getMappedName();
                        }
                        else {
                            descriptor = "L" + descriptor;
                        }
                        if (isArray) {
                            descriptor = "[" + descriptor;
                        }
                    }
                }
                if (descriptor == null) {
                    continue;
                }
                fields.put(AccessSpecifier.of(mappingClass.getMappedName(), field.getMappedName(), descriptor), entry.getValue());
            }
        }
        accesses.clear();
        accesses.putAll(fields);
    }
}
