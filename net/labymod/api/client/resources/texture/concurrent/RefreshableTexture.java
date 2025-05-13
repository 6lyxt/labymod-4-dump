// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture.concurrent;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.GameImage;

public interface RefreshableTexture
{
    @Nullable
    GameImage getCurrentImage();
    
    @NotNull
    CompletableFuture<Void> queueUpdate(@NotNull final GameImage p0);
    
    @Deprecated(forRemoval = true, since = "4.2.17")
    @NotNull
    Object texture();
    
    @NotNull
    Texture parentTexture();
    
    void release();
    
    boolean wasReleased();
    
    default void bindTo(@NotNull final ResourceLocation resourceLocation) {
        Laby.references().resources().textureRepository().register(resourceLocation, this.parentTexture());
    }
}
