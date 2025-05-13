// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication.packets.core;

import net.labymod.core.platform.launcher.communication.LauncherPacketHandler;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.core.platform.launcher.communication.LauncherPacket;

public class LauncherStopPacket implements LauncherPacket
{
    @Override
    public void read(final PayloadReader reader) {
    }
    
    @Override
    public void handle(final LauncherPacketHandler handler) {
        handler.handleStopPacket(this);
    }
}
