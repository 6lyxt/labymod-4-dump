// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

import java.util.Map;
import java.util.Optional;
import net.labymod.api.client.resources.ResourceLocation;

public interface CookieStorage
{
    void setCookie(final ResourceLocation p0, final byte[] p1);
    
    Optional<byte[]> findCookie(final ResourceLocation p0);
    
    Map<ResourceLocation, byte[]> cookies();
}
