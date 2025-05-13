// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.multiplayer.server.payload;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.v1_21_4.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public final class CustomPayloadHolder
{
    private static final Map<ResourceLocation, yn<?, ?>> CODECS;
    
    public static <B extends vl, T extends LabyModCustomPacketPayload> yn<B, T> findOrRegisterCodec(final akv channelId) {
        return findOrRegisterCodec((ResourceLocation)channelId);
    }
    
    public static <B extends vl, T extends LabyModCustomPacketPayload> yn<B, T> findOrRegisterCodec(final ResourceLocation channelId) {
        if (!Laby.references().payloadRegistry().canProcessPayload(channelId)) {
            return null;
        }
        yn codec = CustomPayloadHolder.CODECS.get(channelId);
        if (codec == null) {
            final yn<B, T> newCodec = (yn<B, T>)zz.a(LabyModCustomPacketPayload::write, buffer -> new LabyModCustomPacketPayload(channelId, buffer));
            CustomPayloadHolder.CODECS.put(channelId, newCodec);
            codec = newCodec;
        }
        return (yn<B, T>)codec;
    }
    
    static {
        CODECS = new HashMap<ResourceLocation, yn<?, ?>>();
    }
}
