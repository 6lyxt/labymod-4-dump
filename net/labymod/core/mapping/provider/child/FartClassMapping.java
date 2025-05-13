// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.provider.child;

import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.labymod.api.mapping.provider.child.FieldMapping;
import java.util.Map;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.minecraftforge.srgutils.IMappingFile;

public class FartClassMapping extends FartMapping<IMappingFile.IClass> implements ClassMapping
{
    private final Map<String, FieldMapping> fieldMappings;
    private final Map<String, MethodMapping> methodMappings;
    
    public FartClassMapping(final IMappingFile.IClass delegate) {
        super((IMappingFile.INode)delegate);
        this.fieldMappings = new HashMap<String, FieldMapping>();
        for (final IMappingFile.IField iField : delegate.getFields()) {
            this.fieldMappings.put(iField.getOriginal(), new FartFieldMapping(iField));
        }
        this.methodMappings = new HashMap<String, MethodMapping>();
        for (IMappingFile.IMethod iMethod : delegate.getMethods()) {
            this.methodMappings.put(iMethod.getOriginal() + iMethod.getDescriptor(), new FartMethodMapping(iMethod));
        }
    }
    
    @NotNull
    @Override
    public Collection<FieldMapping> getFieldMappings() {
        return this.fieldMappings.values();
    }
    
    @Override
    public FieldMapping getFieldMapping(final String name) {
        return this.fieldMappings.get(name);
    }
    
    @NotNull
    @Override
    public Collection<MethodMapping> getMethodMappings() {
        return this.methodMappings.values();
    }
    
    @Override
    public MethodMapping getMethodMapping(final String name, final String descriptor) {
        return this.methodMappings.get(name + descriptor);
    }
    
    @NotNull
    @Override
    public String mapField(@NotNull final String name) {
        return ((IMappingFile.IClass)super.delegate).remapField(name);
    }
    
    @NotNull
    @Override
    public String mapMethod(@NotNull final String name, @NotNull final String descriptor) {
        return ((IMappingFile.IClass)super.delegate).remapMethod(name, descriptor);
    }
}
