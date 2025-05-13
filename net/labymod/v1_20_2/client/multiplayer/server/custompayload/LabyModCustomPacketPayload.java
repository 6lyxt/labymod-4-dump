// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.multiplayer.server.custompayload;

import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements vw
{
    private final ResourceLocation id;
    private final byte[] payload;
    
    private LabyModCustomPacketPayload(final ResourceLocation id, final byte[] payload) {
        this.id = id;
        this.payload = payload;
    }
    
    public static LabyModCustomPacketPayload of(final ResourceLocation id, final byte[] payload) {
        return new LabyModCustomPacketPayload(id, payload);
    }
    
    public void a(final so buffer) {
        buffer.c(this.payload);
    }
    
    public aew a() {
        return (aew)this.id;
    }
}
