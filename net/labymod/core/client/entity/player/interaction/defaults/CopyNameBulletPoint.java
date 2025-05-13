// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.api.notification.NotificationController;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class CopyNameBulletPoint extends AbstractBulletPoint
{
    public CopyNameBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.copyUsername", new Component[0]));
    }
    
    @Override
    public void execute(final Player player) {
        final String name = player.getName();
        Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(name);
        final NotificationController notificationController = Laby.labyAPI().notificationController();
        notificationController.push(Notification.builder().title(Component.translatable("labymod.notification.copied", new Component[0])).text(Component.text(name)).duration(4000L).build());
    }
}
