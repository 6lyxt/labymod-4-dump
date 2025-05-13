// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.protocol;

public abstract class Packet
{
    public abstract void read(final PacketBuffer p0);
    
    public abstract void write(final PacketBuffer p0);
    
    public abstract void handle(final PacketHandler p0);
}
