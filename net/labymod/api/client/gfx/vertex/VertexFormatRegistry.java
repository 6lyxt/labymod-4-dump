// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.vertex;

import java.util.Collection;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface VertexFormatRegistry
{
    @Nullable
    VertexFormat getVertexFormat(final String p0);
    
    @Nullable
    VertexFormat getVertexFormat(final ResourceLocation p0);
    
    Collection<VertexFormat> getVertexFormats();
}
