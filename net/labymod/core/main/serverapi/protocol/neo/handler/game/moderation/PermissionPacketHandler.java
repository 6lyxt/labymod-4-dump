// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi.protocol.neo.handler.game.moderation;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.notification.Notification;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.serverapi.core.model.moderation.Permission;
import net.labymod.api.user.permission.ClientPermission;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.core.main.notification.DefaultNotificationController;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;
import net.labymod.serverapi.api.packet.PacketHandler;

public class PermissionPacketHandler implements PacketHandler<PermissionPacket>
{
    private final PermissionRegistry permissionRegistry;
    private final DefaultNotificationController defaultNotificationController;
    
    public PermissionPacketHandler() {
        this.permissionRegistry = Laby.references().permissionRegistry();
        this.defaultNotificationController = (DefaultNotificationController)Laby.references().notificationController();
    }
    
    public void handle(@NotNull final UUID uuid, @NotNull final PermissionPacket packet) {
        final Collection<ClientPermission> enabledPermissions = new HashSet<ClientPermission>();
        final Collection<ClientPermission> disabledPermissions = new HashSet<ClientPermission>();
        for (final Permission.StatedPermission permission : packet.getPermissions()) {
            final ClientPermission clientPermission = this.permissionRegistry.getPermission(permission.permission().getIdentifier());
            if (clientPermission == null) {
                continue;
            }
            final boolean enabled = permission.allowed();
            final boolean permissionEnabled = this.permissionRegistry.isPermissionEnabled(clientPermission.getIdentifier());
            if (enabled == permissionEnabled) {
                continue;
            }
            (enabled ? enabledPermissions : disabledPermissions).add(clientPermission);
            clientPermission.setEnabled(enabled);
        }
        this.defaultNotificationController.push(this.buildNotification(enabledPermissions, true));
        this.defaultNotificationController.push(this.buildNotification(disabledPermissions, false));
    }
    
    private Notification buildNotification(final Collection<ClientPermission> permissions, final boolean enabled) {
        if (permissions.isEmpty()) {
            return null;
        }
        final TextColor color = enabled ? NamedTextColor.DARK_GREEN : NamedTextColor.DARK_RED;
        final TextComponent.Builder builder = Component.text();
        int index = 0;
        final int size = permissions.size();
        final TextComponent.Builder displayNameBuilder = Component.text();
        for (final ClientPermission permission : permissions) {
            displayNameBuilder.append(permission.displayName());
            if (index == size - 1) {
                break;
            }
            if (index == size - 2) {
                ((Component.Builder<T, TextComponent.Builder>)((Component.Builder<T, TextComponent.Builder>)displayNameBuilder).append(Component.text(" "))).append(Component.translatable("labymod.misc.and", new Component[0])).append(Component.text(" "));
                ++index;
            }
            else {
                ((Component.Builder<T, TextComponent.Builder>)displayNameBuilder).append(Component.text(",")).append(Component.text(" "));
                ++index;
            }
        }
        if (index == 1) {
            builder.append(Component.translatable("labymod.notification.permission", displayNameBuilder.build(), Component.translatable("labymod.misc." + (enabled ? "enabled" : "disabled"), color)));
        }
        else {
            builder.append(Component.translatable("labymod.notification.permissions", displayNameBuilder.build(), Component.translatable("labymod.misc." + (enabled ? "enabled" : "disabled"), color)));
        }
        final long duration = 4000L + index * 750L;
        return Notification.builder().title(Component.text("Permissions")).title(Component.text("Permissions")).text(builder.build()).duration(duration).type(Notification.Type.SYSTEM).icon(Icon.currentServer()).build();
    }
}
