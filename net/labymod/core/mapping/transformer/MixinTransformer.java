// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.transformer;

import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import java.util.HashMap;
import java.util.Collections;
import org.objectweb.asm.Type;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.tree.AnnotationNode;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.minecraftforge.fart.internal.EntryImpl;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.ClassRemapper;
import java.util.Map;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.ClassReader;
import net.labymod.api.mapping.provider.MappingProvider;
import net.minecraftforge.fart.api.Transformer;

public class MixinTransformer implements Transformer
{
    private static final String MIXIN_DESC = "Lorg/spongepowered/asm/mixin/Mixin;";
    private final MappingProvider provider;
    
    public MixinTransformer(final MappingProvider provider) {
        this.provider = provider.reverse();
    }
    
    public Transformer.ClassEntry process(final Transformer.ClassEntry entry) {
        final ClassReader reader = new ClassReader(entry.getData());
        ClassNode node = new ClassNode();
        reader.accept((ClassVisitor)node, 0);
        final ClassMapping classMapping = this.findClassMapping(node);
        final Map<String, String> mappings = this.generateMappings(classMapping, node);
        if (!mappings.isEmpty()) {
            node = new ClassNode();
            final ClassRemapper remapper = new ClassRemapper((ClassVisitor)node, (Remapper)new SimpleRemapper((Map)mappings));
            reader.accept((ClassVisitor)remapper, 0);
        }
        final ClassWriter writer = new ClassWriter(reader, 0);
        node.accept((ClassVisitor)writer);
        return (Transformer.ClassEntry)new EntryImpl.ClassEntry(entry.getName(), entry.getTime(), writer.toByteArray());
    }
    
    private ClassMapping findClassMapping(final ClassNode node) {
        final List<AnnotationNode> invisibleAnnotations = node.invisibleAnnotations;
        if (invisibleAnnotations == null) {
            return null;
        }
        for (final AnnotationNode invisibleAnnotation : invisibleAnnotations) {
            if (!invisibleAnnotation.desc.equals("Lorg/spongepowered/asm/mixin/Mixin;")) {
                continue;
            }
            final Map<String, Object> values = ASMHelper.getAnnotationValues(invisibleAnnotation);
            ClassMapping mapping = this.findClassMappingFromValue(values.get("value"));
            if (mapping == null) {
                mapping = this.findClassMappingFromTargets(values.get("targets"));
            }
            return mapping;
        }
        return null;
    }
    
    private ClassMapping findClassMappingFromValue(final Object object) {
        if (object instanceof List) {
            final List<?> list = (List<?>)object;
            for (final Object obj : list) {
                if (obj instanceof final Type type) {
                    final String className = type.getClassName();
                    return this.provider.getClassMapping(className.replace('.', '/'));
                }
            }
            return null;
        }
        return null;
    }
    
    private ClassMapping findClassMappingFromTargets(final Object object) {
        if (object instanceof List) {
            final List<?> list = (List<?>)object;
            for (final Object obj : list) {
                if (obj instanceof final String className) {
                    return this.provider.getClassMapping(className.replace('.', '/'));
                }
            }
            return null;
        }
        return null;
    }
    
    private Map<String, String> generateMappings(final ClassMapping classMapping, final ClassNode classNode) {
        if (classMapping == null) {
            return Collections.emptyMap();
        }
        final Map<String, String> mappings = new HashMap<String, String>();
        for (MethodMapping methodMapping : classMapping.getMethodMappings()) {
            mappings.put(classNode.name + "." + methodMapping.getMappedName() + methodMapping.getDescriptor(), methodMapping.getName());
        }
        for (FieldMapping fieldMapping : classMapping.getFieldMappings()) {
            mappings.put(classNode.name + "." + fieldMapping.getMappedName(), fieldMapping.getName());
        }
        return mappings;
    }
}
