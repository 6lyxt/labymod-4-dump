// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication.packets.addons;

import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.core.platform.launcher.communication.LauncherPacket;

public class LauncherModLoaderChangePacket implements LauncherPacket
{
    private final String modLoader;
    
    public LauncherModLoaderChangePacket(final String modLoader) {
        this.modLoader = modLoader;
    }
    
    @Override
    public void write(final PayloadWriter writer) {
        writer.writeBoolean(this.modLoader != null);
        if (this.modLoader != null) {
            writer.writeString(this.modLoader);
        }
    }
}
