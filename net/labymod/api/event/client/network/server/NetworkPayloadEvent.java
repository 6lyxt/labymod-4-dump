// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.network.server;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.nio.charset.StandardCharsets;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class NetworkPayloadEvent extends DefaultCancellable implements Event
{
    private static final Logging LOGGER;
    private final Side side;
    private ResourceLocation identifier;
    private byte[] payload;
    
    private NetworkPayloadEvent(final Side side, final ResourceLocation identifier, final byte[] payload) {
        this.side = side;
        this.identifier = identifier;
        this.payload = payload;
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static NetworkPayloadEvent createReceive(final ResourceLocation identifier, final byte[] payload) {
        if (DebugRegistry.PAYLOAD.isEnabled()) {
            NetworkPayloadEvent.LOGGER.info("[CustomPayload] [IN] {}: {}", identifier, new String(payload, StandardCharsets.UTF_8));
        }
        return new NetworkPayloadEvent(Side.RECEIVE, identifier, payload);
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static NetworkPayloadEvent createSend(final ResourceLocation identifier, final byte[] payload) {
        if (DebugRegistry.PAYLOAD.isEnabled()) {
            NetworkPayloadEvent.LOGGER.info("[CustomPayload] [OUT] {}: {}", identifier, new String(payload, StandardCharsets.UTF_8));
        }
        return new NetworkPayloadEvent(Side.SEND, identifier, payload);
    }
    
    public Side side() {
        return this.side;
    }
    
    public ResourceLocation identifier() {
        return this.identifier;
    }
    
    public void setIdentifier(final ResourceLocation identifier) {
        this.identifier = identifier;
    }
    
    public byte[] getPayload() {
        return this.payload;
    }
    
    public void setPayload(final byte[] payload) {
        this.payload = payload;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    public enum Side
    {
        RECEIVE, 
        SEND;
    }
}
