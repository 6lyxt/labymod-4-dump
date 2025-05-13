// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication.packets.core;

import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.core.platform.launcher.communication.LauncherPacket;

public class LauncherIdentificationPacket implements LauncherPacket
{
    private final String identifier;
    
    public LauncherIdentificationPacket(final String identifier) {
        this.identifier = identifier;
    }
    
    @Override
    public void write(final PayloadWriter writer) {
        writer.writeString(this.identifier);
    }
}
