// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.icon.ping.providers;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.gui.icon.ping.PingIcon;

public class PingingPingIcon extends PingIcon
{
    private static final ResourceLocation PINGING_1_SPRITE;
    private static final ResourceLocation PINGING_2_SPRITE;
    private static final ResourceLocation PINGING_3_SPRITE;
    private static final ResourceLocation PINGING_4_SPRITE;
    private static final ResourceLocation PINGING_5_SPRITE;
    
    @Override
    public Icon get(final int index) {
        return this.getIcon(this.getPiningSprite(index));
    }
    
    private ResourceLocation getPiningSprite(final int index) {
        return switch (index) {
            case 2 -> PingingPingIcon.PINGING_2_SPRITE;
            case 3 -> PingingPingIcon.PINGING_3_SPRITE;
            case 4 -> PingingPingIcon.PINGING_4_SPRITE;
            case 5 -> PingingPingIcon.PINGING_5_SPRITE;
            default -> PingingPingIcon.PINGING_1_SPRITE;
        };
    }
    
    static {
        PINGING_1_SPRITE = ResourceLocation.create("minecraft", "server_list/pinging_1");
        PINGING_2_SPRITE = ResourceLocation.create("minecraft", "server_list/pinging_2");
        PINGING_3_SPRITE = ResourceLocation.create("minecraft", "server_list/pinging_3");
        PINGING_4_SPRITE = ResourceLocation.create("minecraft", "server_list/pinging_4");
        PINGING_5_SPRITE = ResourceLocation.create("minecraft", "server_list/pinging_5");
    }
}
