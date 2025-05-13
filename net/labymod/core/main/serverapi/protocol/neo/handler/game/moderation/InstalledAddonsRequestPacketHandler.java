// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import net.labymod.api.addon.AddonConfig;
import java.util.Iterator;
import net.labymod.serverapi.api.packet.Packet;
import java.util.List;
import net.labymod.serverapi.core.packet.serverbound.game.moderation.InstalledAddonsResponsePacket;
import net.labymod.api.Laby;
import net.labymod.core.addon.ServerAPIAddonService;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.serverapi.core.model.moderation.InstalledAddon;
import java.util.ArrayList;
import net.labymod.core.addon.DefaultAddonService;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.InstalledAddonsRequestPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class InstalledAddonsRequestPacketHandler implements PacketHandler<InstalledAddonsRequestPacket>
{
    public void handle(@NotNull final UUID uuid, @NotNull final InstalledAddonsRequestPacket packet) {
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        final ServerAPIAddonService serverAPI = addonService.serverAPI();
        final List<String> addons = packet.getAddonsToRequest();
        serverAPI.requestAddons(addons);
        final boolean all = addons.isEmpty();
        final List<InstalledAddon> installedAddons = new ArrayList<InstalledAddon>();
        for (final LoadedAddon loadedAddon : addonService.getLoadedAddons()) {
            final String namespace = loadedAddon.info().getNamespace();
            if (!all && !addons.contains(namespace)) {
                continue;
            }
            final AddonConfig addonConfig = addonService.getMainConfiguration(namespace);
            final boolean disabled = addonConfig != null && addonConfig.enabled() != null && !addonConfig.enabled().get();
            installedAddons.add(ServerAPIAddonService.addonToInstalledAddon(loadedAddon, !disabled));
        }
        Laby.references().labyModProtocolService().sendLabyModPacket((Packet)new InstalledAddonsResponsePacket(packet, (List)installedAddons));
    }
}
