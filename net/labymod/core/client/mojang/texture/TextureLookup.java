// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.mojang.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.UUID;

public interface TextureLookup
{
    ResourceLocation lookup(final UUID p0, final MojangTextureType p1);
}
