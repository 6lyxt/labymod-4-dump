// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

public class ServerListPingIcon extends PingIcon
{
    private static final ResourceLocation UNREACHABLE_PING;
    private static final ResourceLocation PING_1;
    private static final ResourceLocation PING_2;
    private static final ResourceLocation PING_3;
    private static final ResourceLocation PING_4;
    private static final ResourceLocation PING_5;
    
    @Override
    public Icon get(final int milliseconds) {
        return this.getIcon(this.getSprite(milliseconds));
    }
    
    private ResourceLocation getSprite(final int milliseconds) {
        if (milliseconds < 0) {
            return ServerListPingIcon.UNREACHABLE_PING;
        }
        if (milliseconds < 150) {
            return ServerListPingIcon.PING_5;
        }
        if (milliseconds < 300) {
            return ServerListPingIcon.PING_4;
        }
        if (milliseconds < 600) {
            return ServerListPingIcon.PING_3;
        }
        if (milliseconds < 1000) {
            return ServerListPingIcon.PING_2;
        }
        return ServerListPingIcon.PING_1;
    }
    
    static {
        UNREACHABLE_PING = ResourceLocation.create("minecraft", "server_list/unreachable");
        PING_1 = ResourceLocation.create("minecraft", "server_list/ping_1");
        PING_2 = ResourceLocation.create("minecraft", "server_list/ping_2");
        PING_3 = ResourceLocation.create("minecraft", "server_list/ping_3");
        PING_4 = ResourceLocation.create("minecraft", "server_list/ping_4");
        PING_5 = ResourceLocation.create("minecraft", "server_list/ping_5");
    }
}
