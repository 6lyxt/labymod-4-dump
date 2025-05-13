// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.provider.child;

import org.jetbrains.annotations.NotNull;

public interface MethodMapping extends Mapping
{
    @NotNull
    String getDescriptor();
    
    @NotNull
    String getMappedDescriptor();
}
