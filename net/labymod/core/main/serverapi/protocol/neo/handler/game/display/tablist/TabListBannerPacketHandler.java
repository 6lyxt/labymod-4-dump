// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.playerlist.ServerBannerEvent;
import net.labymod.api.util.HashUtil;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class TabListBannerPacketHandler implements PacketHandler<TabListBannerPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final TabListBannerPacket packet) {
        final String iconUrl = packet.getIconUrl();
        Laby.fireEvent(new ServerBannerEvent(iconUrl, HashUtil.md5Hex(iconUrl.getBytes(StandardCharsets.UTF_8))));
    }
}
