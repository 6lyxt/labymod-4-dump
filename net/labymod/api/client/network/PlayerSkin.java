// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network;

import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureType;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;

public interface PlayerSkin
{
    void setSkinTexture(@Nullable final ResourceLocation p0);
    
    void setCapeTexture(@Nullable final ResourceLocation p0);
    
    void setElytraTexture(@Nullable final ResourceLocation p0);
    
    default void setTexture(final MojangTextureType type, final ResourceLocation texture) {
        switch (type) {
            case SKIN: {
                this.setSkinTexture(texture);
                break;
            }
            case CAPE: {
                this.setCapeTexture(texture);
                break;
            }
            case ELYTRA: {
                this.setElytraTexture(texture);
                break;
            }
        }
    }
    
    @NotNull
    default CompletableResourceLocation getCompletableTexture(final MojangTextureType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case SKIN -> this.getCompletableSkinTexture();
            case CAPE -> this.getCompletableCapeTexture();
            case ELYTRA -> this.getCompletableElytraTexture();
        };
    }
    
    void setSkinVariant(@NotNull final MinecraftServices.SkinVariant p0);
    
    @NotNull
    CompletableResourceLocation getCompletableSkinTexture();
    
    @NotNull
    CompletableResourceLocation getCompletableCapeTexture();
    
    @NotNull
    CompletableResourceLocation getCompletableElytraTexture();
    
    @NotNull
    MinecraftServices.SkinVariant getSkinVariant();
    
    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getTexture(final MojangTextureType type) {
        return this.getCompletableTexture(type).getCompleted();
    }
    
    @Deprecated(since = "4.2.14", forRemoval = true)
    @NotNull
    default ResourceLocation getSkinLocation() {
        return this.getCompletableSkinTexture().getCompleted();
    }
    
    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getCapeLocation() {
        return this.getCompletableCapeTexture().getCompleted();
    }
    
    @Deprecated(since = "4.2.14", forRemoval = true)
    @Nullable
    default ResourceLocation getElytraLocation() {
        return this.getCompletableElytraTexture().getCompleted();
    }
}
