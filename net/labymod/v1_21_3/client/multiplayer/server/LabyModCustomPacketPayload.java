// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.multiplayer.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements abf
{
    private final abf.b<? extends abf> type;
    private byte[] buffer;
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final ws buffer) {
        this(id, (byte[])null);
        this.read(buffer);
    }
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final byte[] buffer) {
        this.type = (abf.b<? extends abf>)new abf.b((alz)id.getMinecraftLocation());
        this.buffer = buffer;
    }
    
    public void read(final ws buffer) {
        buffer.b(this.buffer = new byte[buffer.readableBytes()]);
    }
    
    public void write(final ws buffer) {
        buffer.c(this.buffer);
    }
    
    @NotNull
    public abf.b<? extends abf> a() {
        return this.type;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
}
