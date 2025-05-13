// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.Iterator;
import net.labymod.core.addon.ServerAPIAddonService;
import net.labymod.core.addon.DefaultAddonService;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.api.client.component.ComponentUtil;
import java.util.List;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import java.util.ArrayList;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.AddonDisablePacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class AddonDisablePacketHandler implements PacketHandler<AddonDisablePacket>
{
    public static void pushDisableNotification(final String... addonNames) {
        final List<Component> components = new ArrayList<Component>();
        for (final String addonName : addonNames) {
            components.add(Component.text(addonName, NamedTextColor.WHITE));
        }
        pushNotificationInternal(components, false);
    }
    
    private static void pushNotificationInternal(final List<Component> handledAddons, final boolean revert) {
        if (handledAddons.isEmpty()) {
            return;
        }
        final int size = handledAddons.size();
        final String key = (size == 1) ? "labymod.notification.addons.one" : "labymod.notification.addons.multiple";
        final Component component = Component.translatable(key, NamedTextColor.GRAY, ComponentUtil.join(handledAddons), Component.translatable(revert ? "labymod.misc.enabled" : "labymod.misc.disabled", revert ? NamedTextColor.DARK_GREEN : NamedTextColor.DARK_RED));
        Laby.references().notificationController().push(Notification.builder().title(Component.text("Addons")).text(component).duration(5000L + (size - 1) * 750L).type(Notification.Type.SYSTEM).icon(Icon.currentServer()).build());
    }
    
    public void handle(@NotNull final UUID uuid, @NotNull final AddonDisablePacket packet) {
        final DefaultAddonService addonService = DefaultAddonService.getInstance();
        final ServerAPIAddonService serverAPI = addonService.serverAPI();
        final boolean revert = packet.action() == AddonDisablePacket.Action.REVERT;
        final List<Component> handledAddons = new ArrayList<Component>();
        for (final String namespace : packet.getAddonsToDisable()) {
            boolean handled;
            if (revert) {
                handled = serverAPI.revertForceDisable(namespace);
            }
            else {
                handled = serverAPI.forceDisable(namespace);
            }
            if (!handled) {
                continue;
            }
            final InstalledAddonInfo addonInfo = addonService.getAddonInfo(namespace);
            if (addonInfo == null) {
                continue;
            }
            handledAddons.add(Component.text(addonInfo.getDisplayName(), NamedTextColor.WHITE));
        }
        pushNotificationInternal(handledAddons, revert);
    }
}
