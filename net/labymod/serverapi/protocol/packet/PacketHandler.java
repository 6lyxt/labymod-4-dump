// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.packet;

@FunctionalInterface
@Deprecated(forRemoval = true, since = "4.2.24")
public interface PacketHandler<T extends Packet>
{
    void handle(final T p0);
}
