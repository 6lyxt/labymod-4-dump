// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.display;

import net.labymod.serverapi.api.packet.Packet;
import java.util.Iterator;
import net.labymod.core.main.user.subtitle.SubtitleService;
import net.labymod.serverapi.core.model.display.Subtitle;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.display.SubtitlePacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class SubtitlePacketHandler implements PacketHandler<SubtitlePacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final SubtitlePacket packet) {
        final SubtitleService subtitleService = LabyMod.getInstance().getSubtitleService();
        for (final Subtitle subtitle : packet.getSubtitles()) {
            if (subtitle.getText() == null) {
                subtitleService.removeSubtitle(subtitle.getUniqueId());
            }
            else {
                subtitleService.addSubtitle(subtitle);
            }
        }
    }
}
