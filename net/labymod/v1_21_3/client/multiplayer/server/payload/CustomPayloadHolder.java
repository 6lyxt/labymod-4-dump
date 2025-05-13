// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.multiplayer.server.payload;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.v1_21_3.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public final class CustomPayloadHolder
{
    private static final Map<ResourceLocation, zt<?, ?>> CODECS;
    
    public static <B extends ws, T extends LabyModCustomPacketPayload> zt<B, T> findOrRegisterCodec(final alz channelId) {
        return findOrRegisterCodec((ResourceLocation)channelId);
    }
    
    public static <B extends ws, T extends LabyModCustomPacketPayload> zt<B, T> findOrRegisterCodec(final ResourceLocation channelId) {
        if (!Laby.references().payloadRegistry().canProcessPayload(channelId)) {
            return null;
        }
        zt codec = CustomPayloadHolder.CODECS.get(channelId);
        if (codec == null) {
            final zt<B, T> newCodec = (zt<B, T>)abf.a(LabyModCustomPacketPayload::write, buffer -> new LabyModCustomPacketPayload(channelId, buffer));
            CustomPayloadHolder.CODECS.put(channelId, newCodec);
            codec = newCodec;
        }
        return (zt<B, T>)codec;
    }
    
    static {
        CODECS = new HashMap<ResourceLocation, zt<?, ?>>();
    }
}
