// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.resources;

import net.labymod.api.Laby;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;
import net.labymod.api.client.resources.ResourceLocation;

public final class ReleaseTextureEventCaller
{
    public static void call(final ResourceLocation location) {
        Laby.fireEvent(new ReleaseTextureEvent(location));
    }
}
