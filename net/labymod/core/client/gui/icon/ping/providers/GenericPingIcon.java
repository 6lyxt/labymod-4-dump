// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

public class GenericPingIcon extends PingIcon
{
    private static final ResourceLocation PING_UNKNOWN;
    private static final ResourceLocation PING_1;
    private static final ResourceLocation PING_2;
    private static final ResourceLocation PING_3;
    private static final ResourceLocation PING_4;
    private static final ResourceLocation PING_5;
    
    @Override
    public Icon get(final int latency) {
        return this.getIcon(this.getSprite(latency));
    }
    
    private ResourceLocation getSprite(final int latency) {
        if (latency < 0) {
            return GenericPingIcon.PING_UNKNOWN;
        }
        if (latency < 150) {
            return GenericPingIcon.PING_5;
        }
        if (latency < 300) {
            return GenericPingIcon.PING_4;
        }
        if (latency < 600) {
            return GenericPingIcon.PING_3;
        }
        if (latency < 1000) {
            return GenericPingIcon.PING_2;
        }
        return GenericPingIcon.PING_1;
    }
    
    static {
        PING_UNKNOWN = ResourceLocation.create("minecraft", "icon/ping_unknown");
        PING_1 = ResourceLocation.create("minecraft", "icon/ping_1");
        PING_2 = ResourceLocation.create("minecraft", "icon/ping_2");
        PING_3 = ResourceLocation.create("minecraft", "icon/ping_3");
        PING_4 = ResourceLocation.create("minecraft", "icon/ping_4");
        PING_5 = ResourceLocation.create("minecraft", "icon/ping_5");
    }
}
