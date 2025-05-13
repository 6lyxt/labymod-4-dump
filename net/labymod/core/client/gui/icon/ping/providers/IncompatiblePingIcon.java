// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

public class IncompatiblePingIcon extends PingIcon
{
    private static final ResourceLocation INCOMPATIBLE_PING;
    
    @Override
    public Icon get(final int index) {
        return this.getIcon(IncompatiblePingIcon.INCOMPATIBLE_PING);
    }
    
    static {
        INCOMPATIBLE_PING = ResourceLocation.create("minecraft", "server_list/incompatible");
    }
}
