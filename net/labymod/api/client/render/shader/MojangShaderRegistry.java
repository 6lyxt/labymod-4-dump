// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.shader;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MojangShaderRegistry
{
    void addShader(@NotNull final MojangShader p0);
    
    @Nullable
    MojangShader getShader(@NotNull final String p0);
}
