// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.discord;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.feature.DiscordRPC;
import net.labymod.api.Laby;
import java.time.Instant;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class DiscordRPCPacketHandler implements PacketHandler<DiscordRPCPacket>
{
    private static final DefaultDiscordApp DISCORD_APP;
    
    public void handle(@NotNull final UUID uuid, @NotNull final DiscordRPCPacket packet) {
        final DiscordActivity displayedActivity = DiscordRPCPacketHandler.DISCORD_APP.getDisplayedActivity();
        if (displayedActivity == null || displayedActivity.isCustom()) {
            return;
        }
        final DiscordActivity.Builder builder = DiscordActivity.builder(this, displayedActivity);
        final DiscordRPC discordRPC = packet.discordRPC();
        builder.state(discordRPC.getGameMode());
        if (discordRPC.getStartTime() != 0L) {
            builder.start(Instant.ofEpochMilli(discordRPC.getStartTime()));
        }
        if (discordRPC.getEndTime() != 0L) {
            builder.start(Instant.ofEpochMilli(discordRPC.getEndTime()));
        }
        DiscordRPCPacketHandler.DISCORD_APP.displayServerActivity(Laby.labyAPI().serverController().getCurrentServerData(), builder.build());
    }
    
    static {
        DISCORD_APP = (DefaultDiscordApp)Laby.references().discordApp();
    }
}
