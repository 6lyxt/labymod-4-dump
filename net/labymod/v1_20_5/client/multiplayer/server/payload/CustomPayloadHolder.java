// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.multiplayer.server.payload;

import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.v1_20_5.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;

public final class CustomPayloadHolder
{
    private static final Map<ResourceLocation, zn<?, ?>> CODECS;
    
    public static <B extends wm, T extends LabyModCustomPacketPayload> zn<B, T> findOrRegisterCodec(final alf channelId) {
        return findOrRegisterCodec((ResourceLocation)channelId);
    }
    
    public static <B extends wm, T extends LabyModCustomPacketPayload> zn<B, T> findOrRegisterCodec(final ResourceLocation channelId) {
        if (!Laby.references().payloadRegistry().canProcessPayload(channelId)) {
            return null;
        }
        zn codec = CustomPayloadHolder.CODECS.get(channelId);
        if (codec == null) {
            final zn<B, T> newCodec = (zn<B, T>)aax.a(LabyModCustomPacketPayload::write, buffer -> new LabyModCustomPacketPayload(channelId, buffer));
            CustomPayloadHolder.CODECS.put(channelId, newCodec);
            codec = newCodec;
        }
        return (zn<B, T>)codec;
    }
    
    static {
        CODECS = new HashMap<ResourceLocation, zn<?, ?>>();
    }
}
