// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.multiplayer.server.payload;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.v1_21_1.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public final class CustomPayloadHolder
{
    private static final Map<ResourceLocation, yx<?, ?>> CODECS;
    
    public static <B extends vw, T extends LabyModCustomPacketPayload> yx<B, T> findOrRegisterCodec(final akr channelId) {
        return findOrRegisterCodec((ResourceLocation)channelId);
    }
    
    public static <B extends vw, T extends LabyModCustomPacketPayload> yx<B, T> findOrRegisterCodec(final ResourceLocation channelId) {
        if (!Laby.references().payloadRegistry().canProcessPayload(channelId)) {
            return null;
        }
        yx codec = CustomPayloadHolder.CODECS.get(channelId);
        if (codec == null) {
            final yx<B, T> newCodec = (yx<B, T>)aaj.a(LabyModCustomPacketPayload::write, buffer -> new LabyModCustomPacketPayload(channelId, buffer));
            CustomPayloadHolder.CODECS.put(channelId, newCodec);
            codec = newCodec;
        }
        return (yx<B, T>)codec;
    }
    
    static {
        CODECS = new HashMap<ResourceLocation, yx<?, ?>>();
    }
}
