// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.multiplayer.server;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class LabyModCustomPacketPayload implements aax
{
    private final aax.b<? extends aax> type;
    private byte[] buffer;
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final wm buffer) {
        this(id, (byte[])null);
        this.read(buffer);
    }
    
    public LabyModCustomPacketPayload(final ResourceLocation id, final byte[] buffer) {
        this.type = (aax.b<? extends aax>)new aax.b((alf)id.getMinecraftLocation());
        this.buffer = buffer;
    }
    
    public void read(final wm buffer) {
        buffer.b(this.buffer = new byte[buffer.readableBytes()]);
    }
    
    public void write(final wm buffer) {
        buffer.c(this.buffer);
    }
    
    @NotNull
    public aax.b<? extends aax> a() {
        return this.type;
    }
    
    public byte[] getBuffer() {
        return this.buffer;
    }
}
