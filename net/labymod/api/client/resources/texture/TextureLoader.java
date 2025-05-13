// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import java.io.IOException;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.net.URI;

public interface TextureLoader
{
    boolean canLoad(final URI p0);
    
    void loadTexture(final URI p0, @Nullable final ResourceLocation p1, final CompletableTextureImage p2) throws IOException;
}
