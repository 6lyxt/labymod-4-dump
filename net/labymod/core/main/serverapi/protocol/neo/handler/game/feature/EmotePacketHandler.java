// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import java.util.Iterator;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.serverapi.core.model.feature.Emote;
import net.labymod.core.main.user.DefaultGameUserService;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUserService;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class EmotePacketHandler implements PacketHandler<EmotePacket>
{
    private final GameUserService gameUserService;
    
    public EmotePacketHandler() {
        this.gameUserService = Laby.references().gameUserService();
    }
    
    public void handle(@NotNull final UUID uuid, @NotNull final EmotePacket packet) {
        final EmoteService emoteService = ((DefaultGameUserService)this.gameUserService).emoteService();
        for (final Emote emote : packet.getEmotes()) {
            final EmoteItem targetEmote = emoteService.getEmote(emote.getEmoteId());
            if (targetEmote == null) {
                continue;
            }
            if (targetEmote.isDraft() && !this.gameUserService.clientGameUser().visibleGroup().isStaffOrCosmeticCreator()) {
                continue;
            }
            emoteService.getOrCreateAnimationStorage(emote.getUniqueId()).playEmote(targetEmote);
        }
    }
}
