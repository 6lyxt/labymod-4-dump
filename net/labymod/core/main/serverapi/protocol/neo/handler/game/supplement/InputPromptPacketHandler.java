// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi.InputPromptActivity;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.game.supplement.InputPromptResponsePacket;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.InputPromptPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class InputPromptPacketHandler implements PacketHandler<InputPromptPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final InputPromptPacket packet) {
        final InputPromptActivity activity = new InputPromptActivity(packet.prompt(), value -> Laby.references().labyModProtocolService().sendLabyModPacket((Packet)new InputPromptResponsePacket(packet, value)));
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
}
