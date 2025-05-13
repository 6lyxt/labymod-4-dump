// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.multiplayer.server.payload;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.v1_21_5.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public final class CustomPayloadHolder
{
    private static final Map<ResourceLocation, ze<?, ?>> CODECS;
    
    public static <B extends vy, T extends LabyModCustomPacketPayload> ze<B, T> findOrRegisterCodec(final alr channelId) {
        return findOrRegisterCodec((ResourceLocation)channelId);
    }
    
    public static <B extends vy, T extends LabyModCustomPacketPayload> ze<B, T> findOrRegisterCodec(final ResourceLocation channelId) {
        if (!Laby.references().payloadRegistry().canProcessPayload(channelId)) {
            return null;
        }
        ze codec = CustomPayloadHolder.CODECS.get(channelId);
        if (codec == null) {
            final ze<B, T> newCodec = (ze<B, T>)aat.a(LabyModCustomPacketPayload::write, buffer -> new LabyModCustomPacketPayload(channelId, buffer));
            CustomPayloadHolder.CODECS.put(channelId, newCodec);
            codec = newCodec;
        }
        return (ze<B, T>)codec;
    }
    
    static {
        CODECS = new HashMap<ResourceLocation, ze<?, ?>>();
    }
}
