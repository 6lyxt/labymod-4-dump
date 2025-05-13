// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.multiplayer.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements aaj
{
    private final aaj.b<? extends aaj> type;
    private byte[] buffer;
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final vw buffer) {
        this(id, (byte[])null);
        this.read(buffer);
    }
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final byte[] buffer) {
        this.type = (aaj.b<? extends aaj>)new aaj.b((akr)id.getMinecraftLocation());
        this.buffer = buffer;
    }
    
    public void read(final vw buffer) {
        buffer.b(this.buffer = new byte[buffer.readableBytes()]);
    }
    
    public void write(final vw buffer) {
        buffer.c(this.buffer);
    }
    
    @NotNull
    public aaj.b<? extends aaj> a() {
        return this.type;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
}
