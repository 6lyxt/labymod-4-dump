// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mojang.texture;

import java.util.function.Consumer;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MojangTextureService
{
    public static final UUID DEFAULT_UUID = new UUID(0L, 0L);
    
    CompletableResourceLocation getTexture(final UUID p0, final MojangTextureType p1);
    
    CompletableResourceLocation getTexture(final String p0, final MojangTextureType p1);
    
    MinecraftServices.SkinVariant getVariant(final ResourceLocation p0);
    
    ResourceLocation getDefaultTexture(final UUID p0, final MojangTextureType p1);
    
    void applyTexture(final UUID p0, final MojangTextureType p1, final String p2);
    
    void applySkinTexture(final UUID p0, final MinecraftServices.SkinVariant p1, final String p2);
    
    default void getTexture(final UUID uuid, final MojangTextureType type, final Consumer<ResourceLocation> textureResource) {
        final CompletableResourceLocation completableTexture = this.getTexture(uuid, type);
        textureResource.accept(completableTexture.getCompleted());
        completableTexture.addCompletableListener(() -> textureResource.accept(completableTexture.getCompleted()));
    }
    
    default ResourceLocation getDefaultTexture(final MojangTextureType type) {
        return this.getDefaultTexture(MojangTextureService.DEFAULT_UUID, type);
    }
}
