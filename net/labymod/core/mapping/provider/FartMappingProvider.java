// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.provider;

import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.core.mapping.provider.child.FartClassMapping;
import java.util.HashMap;
import net.labymod.api.mapping.provider.child.ClassMapping;
import java.util.Map;
import net.minecraftforge.srgutils.IMappingFile;
import net.labymod.api.mapping.provider.MappingProvider;

public class FartMappingProvider implements MappingProvider
{
    private final String sourceNamespace;
    private final String targetNamespace;
    private final IMappingFile delegate;
    private final Map<String, ClassMapping> classMappings;
    
    public FartMappingProvider(final String sourceNamespace, final String targetNamespace, final IMappingFile delegate) {
        this.sourceNamespace = sourceNamespace;
        this.targetNamespace = targetNamespace;
        this.delegate = delegate;
        this.classMappings = new HashMap<String, ClassMapping>();
        for (final IMappingFile.IClass iClass : delegate.getClasses()) {
            this.classMappings.put(iClass.getOriginal(), new FartClassMapping(iClass));
        }
    }
    
    @NotNull
    @Override
    public String getSourceNamespace() {
        return this.sourceNamespace;
    }
    
    @NotNull
    @Override
    public String getTargetNamespace() {
        return this.targetNamespace;
    }
    
    @NotNull
    @Override
    public Collection<ClassMapping> getClassMappings() {
        return this.classMappings.values();
    }
    
    @Nullable
    @Override
    public ClassMapping getClassMapping(final String name) {
        return this.classMappings.get(name);
    }
    
    @NotNull
    @Override
    public String mapClass(@NotNull final String name) {
        return this.delegate.remapClass(name);
    }
    
    @NotNull
    @Override
    public String mapDescriptor(@NotNull final String descriptor) {
        return this.delegate.remapDescriptor(descriptor);
    }
    
    @NotNull
    @Override
    public MappingProvider reverse() {
        return new FartMappingProvider(this.targetNamespace, this.sourceNamespace, this.delegate.reverse());
    }
    
    @NotNull
    @Override
    public MappingProvider chain(@NotNull final MappingProvider other) {
        if (other instanceof final FartMappingProvider fartOther) {
            return new FartMappingProvider(this.sourceNamespace, fartOther.getTargetNamespace(), this.delegate.chain(fartOther.delegate));
        }
        throw new IllegalArgumentException("Cannot chain with non-FartMappingProvider");
    }
    
    @NotNull
    @Override
    public MappingProvider sourceMappings() {
        return this.chain(this.reverse());
    }
    
    @NotNull
    @Override
    public MappingProvider targetMappings() {
        return this.reverse().chain(this);
    }
    
    public IMappingFile getDelegate() {
        return this.delegate;
    }
}
