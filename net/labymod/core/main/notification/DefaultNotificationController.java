// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.notification;

import java.util.Iterator;
import net.labymod.api.event.labymod.notification.PushNotificationEvent;
import net.labymod.api.event.labymod.notification.PopNotificationEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.notification.UpdateNotificationEvent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.Subscribe;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.event.EventBus;
import net.labymod.api.notification.Notification;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.notification.NotificationController;

@Singleton
@Implements(NotificationController.class)
public final class DefaultNotificationController implements NotificationController
{
    private final List<Notification> displayedNotifications;
    private final List<Notification> waitingNotifications;
    
    @Inject
    public DefaultNotificationController(final EventBus eventBus) {
        this.displayedNotifications = new ArrayList<Notification>();
        this.waitingNotifications = new ArrayList<Notification>();
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onGameTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Notification notification = CollectionHelper.get(this.displayedNotifications, n -> !n.isAlive());
        this.pop(notification);
    }
    
    @Override
    public void push(final Notification notification) {
        if (notification == null || !notification.type().shouldPush()) {
            return;
        }
        if (!ThreadSafe.isRenderThread()) {
            ThreadSafe.executeOnRenderThread(() -> this.push(notification));
            return;
        }
        final Notification displayedNotification = this.getMatchingDisplayed(notification);
        if (displayedNotification != null) {
            displayedNotification.resetProgress();
            displayedNotification.addIndex();
            Laby.fireEvent(new UpdateNotificationEvent(displayedNotification));
            return;
        }
        this.addNotification(notification, true);
    }
    
    @Override
    public void pop(final Notification notification) {
        if (notification == null) {
            return;
        }
        if (!ThreadSafe.isRenderThread()) {
            ThreadSafe.executeOnRenderThread(() -> this.pop(notification));
            return;
        }
        if (!this.displayedNotifications.remove(notification) && !this.waitingNotifications.remove(notification)) {
            return;
        }
        Laby.labyAPI().eventBus().fire(new PopNotificationEvent(notification));
        if (this.waitingNotifications.isEmpty()) {
            return;
        }
        final Integer maxNotifications = Laby.labyAPI().config().notifications().maxNotifications().get();
        while (this.displayedNotifications.size() <= maxNotifications && !this.waitingNotifications.isEmpty()) {
            final Notification nextNotification = this.waitingNotifications.remove(0);
            nextNotification.resetProgress();
            this.addNotification(nextNotification, false);
        }
    }
    
    public List<Notification> getDisplayedNotifications() {
        return this.displayedNotifications;
    }
    
    public List<Notification> getWaitingNotifications() {
        return this.waitingNotifications;
    }
    
    private void addNotification(final Notification notification, final boolean addToWaiting) {
        if (notification.type() != Notification.Type.SYSTEM && !Laby.labyAPI().config().notifications().enabled().get()) {
            return;
        }
        if (this.displayedNotifications.size() >= Laby.labyAPI().config().notifications().maxNotifications().get()) {
            if (addToWaiting) {
                this.waitingNotifications.add(notification);
            }
            return;
        }
        this.displayedNotifications.add(notification);
        Laby.fireEvent(new PushNotificationEvent(notification));
    }
    
    private Notification getMatchingDisplayed(final Notification notification) {
        for (final Notification displayedNotification : this.displayedNotifications) {
            if (displayedNotification.equals(notification)) {
                return displayedNotification;
            }
        }
        for (final Notification waitingNotification : this.waitingNotifications) {
            if (waitingNotification.equals(notification)) {
                return waitingNotification;
            }
        }
        return null;
    }
}
