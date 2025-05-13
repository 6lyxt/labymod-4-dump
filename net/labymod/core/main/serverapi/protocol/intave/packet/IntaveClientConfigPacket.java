// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.intave.packet;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.Laby;
import net.labymod.serverapi.api.packet.Packet;

public class IntaveClientConfigPacket implements Packet
{
    private boolean legacySneakHeight;
    private boolean legacyOldRange;
    private boolean legacyOldSlowdown;
    
    public void readConfig() {
        final LabyConfig config = Laby.labyAPI().config();
        final PermissionRegistry permissionRegistry = Laby.references().permissionRegistry();
        final ClassicPvPConfig classicPvP = config.multiplayer().classicPvP();
        final boolean oldSneaking = permissionRegistry.isPermissionEnabled("animations", classicPvP.oldSword());
        final boolean oldSlowdown = permissionRegistry.isPermissionEnabled("slowdown", classicPvP.oldSlowdown());
        final boolean oldRange = permissionRegistry.isPermissionEnabled("range", classicPvP.oldRange());
        this.setLegacySneakHeight(oldSneaking);
        this.setLegacyOldSlowdown(oldSlowdown);
        this.setLegacyOldRange(oldRange);
    }
    
    public boolean isLegacySneakHeight() {
        return this.legacySneakHeight;
    }
    
    public void setLegacySneakHeight(final boolean legacySneakHeight) {
        this.legacySneakHeight = legacySneakHeight;
    }
    
    public boolean isLegacyOldRange() {
        return this.legacyOldRange;
    }
    
    public void setLegacyOldRange(final boolean legacyOldRange) {
        this.legacyOldRange = legacyOldRange;
    }
    
    public boolean isLegacyOldSlowdown() {
        return this.legacyOldSlowdown;
    }
    
    public void setLegacyOldSlowdown(final boolean legacyOldSlowdown) {
        this.legacyOldSlowdown = legacyOldSlowdown;
    }
}
