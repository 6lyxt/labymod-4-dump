// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.multiplayer.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements aat
{
    private final aat.b<? extends aat> type;
    private byte[] buffer;
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final vy buffer) {
        this(id, (byte[])null);
        this.read(buffer);
    }
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final byte[] buffer) {
        this.type = (aat.b<? extends aat>)new aat.b((alr)id.getMinecraftLocation());
        this.buffer = buffer;
    }
    
    public void read(final vy buffer) {
        buffer.b(this.buffer = new byte[buffer.readableBytes()]);
    }
    
    public void write(final vy buffer) {
        buffer.c(this.buffer);
    }
    
    @NotNull
    public aat.b<? extends aat> a() {
        return this.type;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
}
