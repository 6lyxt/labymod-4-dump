// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping.provider.child;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.mapping.provider.child.Mapping;
import net.minecraftforge.srgutils.IMappingFile;

public class FartMapping<T extends IMappingFile.INode> implements Mapping
{
    protected final T delegate;
    
    public FartMapping(final T delegate) {
        this.delegate = delegate;
    }
    
    @NotNull
    @Override
    public String getName() {
        return this.delegate.getOriginal();
    }
    
    @NotNull
    @Override
    public String getMappedName() {
        return this.delegate.getMapped();
    }
}
