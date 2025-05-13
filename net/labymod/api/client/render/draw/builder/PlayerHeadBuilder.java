// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.GameProfile;
import java.util.UUID;

public interface PlayerHeadBuilder<T extends PlayerHeadBuilder<T>> extends RectangleBuilder<T>
{
    T player(final UUID p0);
    
    T player(final GameProfile p0);
    
    T player(final ResourceLocation p0);
    
    T wearingHat(final boolean p0);
}
