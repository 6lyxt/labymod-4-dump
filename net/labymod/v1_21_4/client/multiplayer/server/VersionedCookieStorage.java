// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.multiplayer.server;

import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.network.server.CookieStorage;

public class VersionedCookieStorage implements CookieStorage
{
    private final ggt transferState;
    
    public VersionedCookieStorage(final ggt transferState) {
        this.transferState = transferState;
    }
    
    @Override
    public void setCookie(final ResourceLocation cookieId, final byte[] data) {
        this.transferState.a().put(cookieId.getMinecraftLocation(), data);
    }
    
    @Override
    public Optional<byte[]> findCookie(final ResourceLocation cookieId) {
        return Optional.ofNullable(this.transferState.a().get(cookieId.getMinecraftLocation()));
    }
    
    @Override
    public Map<ResourceLocation, byte[]> cookies() {
        final Map<akv, byte[]> vanillaCookies = this.transferState.a();
        final Map<ResourceLocation, byte[]> cookies = new HashMap<ResourceLocation, byte[]>(vanillaCookies.size());
        for (final Map.Entry<akv, byte[]> entry : vanillaCookies.entrySet()) {
            cookies.put((ResourceLocation)entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap((Map<? extends ResourceLocation, ? extends byte[]>)cookies);
    }
}
