// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication;

import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.api.payload.io.PayloadReader;

public interface LauncherPacket
{
    default void read(final PayloadReader reader) {
        throw new UnsupportedOperationException("Write only packet!");
    }
    
    default void write(final PayloadWriter writer) {
        throw new UnsupportedOperationException("Read only packet!");
    }
    
    default void handle(final LauncherPacketHandler handler) {
    }
}
