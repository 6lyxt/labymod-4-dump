// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping.provider.child;

import org.jetbrains.annotations.Nullable;

public interface FieldMapping extends Mapping
{
    @Nullable
    String getDescriptor();
    
    @Nullable
    String getMappedDescriptor();
}
