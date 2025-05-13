// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.notification;

import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.notification.Notification;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class NotificationStack extends VerticalListWidget<NotificationWidget>
{
    @Override
    public void render(final ScreenContext context) {
        float shiftY = 0.0f;
        for (final NotificationWidget widget : this.children) {
            shiftY -= this.updateShift(widget, widget.notification(), shiftY);
        }
        this.setVisible(!this.children.isEmpty());
        super.render(context);
    }
    
    private float updateShift(final NotificationWidget widget, final Notification notification, final float progressY) {
        final float timeAlive = (float)notification.timeAlive();
        final float duration = (float)notification.duration();
        final float progressIn = 1.0f - timeAlive / 800.0f;
        final float progressOut = 1.0f - (duration - timeAlive) / 800.0f;
        final float progressX = (timeAlive >= 0.0f && timeAlive <= 800.0f) ? progressIn : ((timeAlive >= duration - 800.0f && timeAlive <= duration) ? progressOut : ((timeAlive > duration) ? 1.0f : 0.0f));
        final float x = widget.bounds().getWidth(BoundsType.OUTER) * MathHelper.sigmoid(progressX) * 2.0f;
        widget.translateX().set(x);
        widget.translateY().set(progressY);
        return progressX * widget.bounds().getHeight(BoundsType.OUTER);
    }
    
    public void push(final Notification notification, final boolean fadeIn) {
        final NotificationWidget widget = new NotificationWidget(notification);
        if (fadeIn) {
            this.addChildAsync(widget);
        }
        else {
            this.addChild(widget);
        }
    }
    
    public void pop(final Notification notification) {
        this.removeChildIf(NotificationWidget.class, notificationWidget -> notificationWidget.notification().equals(notification));
    }
    
    public void update(final Notification notification) {
        final NotificationWidget widget = CollectionHelper.get((Collection<NotificationWidget>)this.children, n -> n.notification().equals(notification));
        if (widget != null) {
            this.removeChild(widget);
            this.push(notification, true);
        }
    }
    
    @Override
    protected boolean canHover() {
        for (final NotificationWidget child : this.children) {
            if (child.canHover()) {
                return true;
            }
        }
        return false;
    }
}
