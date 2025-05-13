// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.network.server.servers;

import net.labymod.api.event.Phase;
import net.labymod.core.main.user.permission.DefaultPermissionRegistry;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.AbstractServer;

public final class HypixelServer extends AbstractServer
{
    private static final String NAME = "hypixel";
    private final LabyAPI labyAPI;
    
    public HypixelServer() {
        super("hypixel");
        this.labyAPI = Laby.labyAPI();
    }
    
    @Override
    public void loginOrSwitch(final LoginPhase phase) {
        final DefaultPermissionRegistry registry = (DefaultPermissionRegistry)this.labyAPI.permissionRegistry();
        registry.disableAllPermissions();
        registry.setPermission("animations", true);
        registry.setPermission("blockbuild", true);
    }
    
    @Override
    public void disconnect(final Phase phase) {
    }
}
