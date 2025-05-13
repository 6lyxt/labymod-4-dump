// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.display.tablist;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.user.GameUser;
import java.util.Iterator;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.core.model.display.TabListFlag;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListFlagPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class TabListFlagPacketHandler implements PacketHandler<TabListFlagPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final TabListFlagPacket packet) {
        for (final TabListFlag flag : packet.getFlags()) {
            final GameUser gameUser = LabyMod.getInstance().gameUserService().gameUser(flag.getUniqueId());
            if (gameUser instanceof DefaultGameUser) {
                final CountryCode countryCode = CountryCode.fromCode(flag.getCountryCode().name());
                ((DefaultGameUser)gameUser).setCountryCode(countryCode);
            }
        }
    }
}
