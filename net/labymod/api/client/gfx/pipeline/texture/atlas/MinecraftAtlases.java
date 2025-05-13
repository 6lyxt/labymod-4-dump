// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.atlas;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MinecraftAtlases
{
    @Nullable
    default TextureAtlas getGuiAtlas() {
        return null;
    }
}
