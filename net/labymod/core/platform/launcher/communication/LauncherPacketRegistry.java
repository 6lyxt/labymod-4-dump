// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication;

import java.util.Iterator;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherModLoaderChangePacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherIncompatibleAddonPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherStopPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherWindowCreatedPacket;
import net.labymod.core.platform.launcher.communication.packets.core.LauncherIdentificationPacket;
import java.util.HashMap;
import java.util.Map;

public class LauncherPacketRegistry
{
    private final Map<Integer, Class<? extends LauncherPacket>> packets;
    
    public LauncherPacketRegistry() {
        (this.packets = new HashMap<Integer, Class<? extends LauncherPacket>>()).put(0, LauncherIdentificationPacket.class);
        this.packets.put(1, LauncherWindowCreatedPacket.class);
        this.packets.put(2, LauncherStopPacket.class);
        this.packets.put(10, LauncherAddonInstalledPacket.class);
        this.packets.put(11, LauncherIncompatibleAddonPacket.class);
        this.packets.put(12, LauncherModLoaderChangePacket.class);
    }
    
    public Class<? extends LauncherPacket> getPacketById(final int id) {
        return this.packets.get(id);
    }
    
    public int getIdByPacket(final Class<? extends LauncherPacket> packet) {
        for (final Map.Entry<Integer, Class<? extends LauncherPacket>> entry : this.packets.entrySet()) {
            if (entry.getValue().equals(packet)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
