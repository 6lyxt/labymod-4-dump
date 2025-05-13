// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mojang;

import com.google.gson.JsonObject;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureType;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.metadata.MetadataExtension;

public interface GameProfile extends MetadataExtension
{
    UUID getUniqueId();
    
    String getUsername();
    
    @NotNull
    Map<String, Collection<Property>> getProperties();
    
    default boolean hasProperty(@NotNull final String key) {
        return this.getProperties().containsKey(key);
    }
    
    default CompletableResourceLocation getTexture(final MojangTextureType type) {
        return Laby.references().mojangTextureService().getTexture(this.getUniqueId(), type);
    }
    
    GameProfile copy();
    
    @Deprecated
    UUID getProfileId();
    
    @Deprecated
    JsonObject getTexturesJson();
}
