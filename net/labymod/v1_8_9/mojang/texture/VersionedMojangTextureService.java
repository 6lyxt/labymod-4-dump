// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mojang.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.UUID;
import net.labymod.api.LabyAPI;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.mojang.texture.DefaultMojangTextureService;

@Singleton
@Implements(MojangTextureService.class)
public class VersionedMojangTextureService extends DefaultMojangTextureService
{
    public VersionedMojangTextureService(final LabyAPI labyAPI) {
        super(labyAPI);
    }
    
    @Override
    public ResourceLocation getDefaultTexture(final UUID profileId, final MojangTextureType type) {
        if (type == MojangTextureType.SKIN) {
            return (ResourceLocation)bmz.a(profileId);
        }
        return null;
    }
}
