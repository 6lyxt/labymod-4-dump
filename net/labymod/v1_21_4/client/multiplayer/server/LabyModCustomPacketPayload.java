// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.multiplayer.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements zz
{
    private final zz.b<? extends zz> type;
    private byte[] buffer;
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final vl buffer) {
        this(id, (byte[])null);
        this.read(buffer);
    }
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final byte[] buffer) {
        this.type = (zz.b<? extends zz>)new zz.b((akv)id.getMinecraftLocation());
        this.buffer = buffer;
    }
    
    public void read(final vl buffer) {
        buffer.b(this.buffer = new byte[buffer.readableBytes()]);
    }
    
    public void write(final vl buffer) {
        buffer.c(this.buffer);
    }
    
    @NotNull
    public zz.b<? extends zz> a() {
        return this.type;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
}
