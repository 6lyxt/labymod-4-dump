// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.spray;

import java.nio.ByteBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.DefaultLabyConnect;

public interface SprayProtocol
{
    void handle(final DefaultLabyConnect p0, final short p1, final int p2, final UUID p3, final ByteBuffer p4);
}
