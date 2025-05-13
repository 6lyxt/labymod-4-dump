// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;

public interface OptiFinePlayer
{
    @Nullable
    ResourceLocation getOptiFineCapeLocation();
    
    void bridge$optifine$setReloadCapeTime(final long p0);
}
