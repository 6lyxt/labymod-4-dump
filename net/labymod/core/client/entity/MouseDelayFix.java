// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity;

import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.user.permission.PermissionRegistry;

public class MouseDelayFix
{
    public static final PermissionRegistry REGISTRY;
    public static final ConfigProperty<Boolean> PROPERTY;
    
    public static boolean isEnabled() {
        return MouseDelayFix.PROPERTY.get() && MouseDelayFix.REGISTRY.isPermissionEnabled("crosshair_sync");
    }
    
    static {
        REGISTRY = Laby.references().permissionRegistry();
        PROPERTY = Laby.labyAPI().config().ingame().mouseDelayFix();
    }
}
