// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.labymod.notification.UpdateNotificationEvent;
import net.labymod.api.event.labymod.notification.PopNotificationEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.notification.PushNotificationEvent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.notification.Notification;
import net.labymod.core.main.notification.DefaultNotificationController;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.notification.NotificationController;
import net.labymod.core.client.gui.screen.widget.widgets.notification.NotificationStack;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;

@Link("activity/notification.lss")
@AutoActivity
public class NotificationOverlay extends ScreenOverlay
{
    private NotificationStack notificationList;
    private NotificationController notificationController;
    
    public NotificationOverlay() {
        super(32767);
        this.setActive(true);
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (this.notificationController == null) {
            this.notificationController = Laby.references().notificationController();
        }
        super.initialize(parent);
        ((AbstractWidget<Widget>)(this.notificationList = new NotificationStack())).addId("notification-list");
        for (final Notification notification : ((DefaultNotificationController)this.notificationController).getDisplayedNotifications()) {
            this.notificationList.push(notification, false);
        }
        ((AbstractWidget<NotificationStack>)this.document).addChild(this.notificationList);
    }
    
    @Subscribe
    public void onPushNotification(final PushNotificationEvent event) {
        if (this.notificationList != null) {
            this.notificationList.push(event.notification(), true);
        }
    }
    
    @Subscribe
    public void onPopNotification(final PopNotificationEvent event) {
        if (this.notificationList != null) {
            this.notificationList.pop(event.notification());
        }
    }
    
    @Subscribe
    public void onUpdateNotification(final UpdateNotificationEvent event) {
        if (this.notificationList != null) {
            this.notificationList.update(event.notification());
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        ((DefaultNotificationController)this.notificationController).getDisplayedNotifications().clear();
    }
}
