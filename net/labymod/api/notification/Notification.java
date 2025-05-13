// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification;

import net.labymod.api.notification.type.SocialNotificationType;
import net.labymod.api.notification.type.DefaultNotificationType;
import net.labymod.api.Laby;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Objects;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public class Notification
{
    public static final long FADE_DURATION = 800L;
    private final Component title;
    private final Component text;
    private final Icon icon;
    private final Icon secondaryIcon;
    private final Icon thumbnail;
    private final long duration;
    private final NotificationType type;
    private final Consumer<Notification> onClick;
    private final List<NotificationButton> buttons;
    private long startTime;
    private long holdTime;
    private int index;
    
    private Notification(final Component title, final Component text, final Icon icon, final Icon secondaryIcon, final Icon thumbnail, final long duration, final NotificationType type, final List<NotificationButton> buttons, final Consumer<Notification> onClick) {
        this.holdTime = -1L;
        this.title = title;
        this.text = text;
        this.icon = icon;
        this.secondaryIcon = secondaryIcon;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.type = type;
        this.buttons = buttons;
        this.onClick = onClick;
        this.resetProgress();
    }
    
    @Contract(value = " -> new", pure = true)
    @NotNull
    public static Builder builder() {
        return new Builder();
    }
    
    @NotNull
    public static Builder builder(final Notification notification) {
        return new Builder(notification);
    }
    
    @ApiStatus.Experimental
    public List<NotificationButton> buttons() {
        return this.buttons;
    }
    
    public Component title() {
        return this.title;
    }
    
    public Component text() {
        return this.text;
    }
    
    public Icon icon() {
        return this.icon;
    }
    
    public Icon secondaryIcon() {
        return this.secondaryIcon;
    }
    
    public Icon thumbnail() {
        return this.thumbnail;
    }
    
    public long duration() {
        return this.duration;
    }
    
    public NotificationType type() {
        return this.type;
    }
    
    public float progress() {
        if (!this.isAlive()) {
            return 1.0f;
        }
        final float visibleTimeAlive = (float)(this.timeAlive() - 800L);
        final float maxVisibleDuration = (float)(this.duration - 1600L);
        return MathHelper.clamp(visibleTimeAlive / maxVisibleDuration, 0.0f, 1.0f);
    }
    
    public void resetProgress() {
        this.startTime = TimeUtil.getMillis();
    }
    
    public void holdProgress() {
        if (this.holdTime != -1L || this.isFading()) {
            return;
        }
        this.holdTime = this.timeAlive();
    }
    
    public void resumeProgress() {
        if (this.holdTime == -1L) {
            return;
        }
        this.startTime = TimeUtil.getMillis() - this.holdTime;
        this.holdTime = -1L;
    }
    
    public boolean isAlive() {
        return this.timeAlive() <= this.duration;
    }
    
    public long timeAlive() {
        if (this.holdTime != -1L) {
            return this.holdTime;
        }
        return TimeUtil.getMillis() - this.startTime;
    }
    
    public boolean isFading() {
        return this.isFadingIn() || this.isFadingOut();
    }
    
    public boolean isFadingIn() {
        return this.timeAlive() <= 800L;
    }
    
    public boolean isFadingOut() {
        return this.timeAlive() >= this.duration - 800L;
    }
    
    public void remove() {
        this.resumeProgress();
        this.startTime = TimeUtil.getMillis() - (this.duration - 800L);
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public boolean click() {
        if (this.onClick != null && this.isAlive()) {
            this.onClick.accept(this);
            this.remove();
            return true;
        }
        return false;
    }
    
    @ApiStatus.Internal
    public void addIndex() {
        ++this.index;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Notification that = (Notification)o;
        return this.duration == that.duration && Objects.equals(this.buttons, that.buttons) && Objects.equals(this.title, that.title) && Objects.equals(this.text, that.text) && Objects.equals(this.icon, that.icon) && Objects.equals(this.type, that.type);
    }
    
    @Override
    public int hashCode() {
        int result = (this.buttons != null) ? this.buttons.hashCode() : 0;
        result = 31 * result + ((this.title != null) ? this.title.hashCode() : 0);
        result = 31 * result + ((this.text != null) ? this.text.hashCode() : 0);
        result = 31 * result + ((this.icon != null) ? this.icon.hashCode() : 0);
        result = 31 * result + (int)(this.duration ^ this.duration >>> 32);
        result = 31 * result + ((this.type != null) ? this.type.hashCode() : 0);
        return result;
    }
    
    public static class Builder
    {
        private Component title;
        private Component text;
        private Icon icon;
        private Icon secondaryIcon;
        private Icon thumbnail;
        private long duration;
        private NotificationType type;
        private Consumer<Notification> onClick;
        private final List<NotificationButton> buttons;
        
        private Builder() {
            this.duration = 7000L;
            this.type = Type.SYSTEM;
            this.buttons = new ArrayList<NotificationButton>();
        }
        
        private Builder(final Notification notification) {
            this.duration = 7000L;
            this.type = Type.SYSTEM;
            this.title = notification.title;
            this.text = notification.text;
            this.icon = notification.icon;
            this.secondaryIcon = notification.secondaryIcon;
            this.thumbnail = notification.thumbnail;
            this.duration = notification.duration;
            this.type = notification.type;
            this.onClick = notification.onClick;
            this.buttons = notification.buttons;
        }
        
        public Builder title(final Component title) {
            this.title = title;
            return this;
        }
        
        public Builder text(final Component text) {
            this.text = text;
            return this;
        }
        
        public Builder icon(final Icon icon) {
            this.icon = icon;
            return this;
        }
        
        public Builder secondaryIcon(final Icon icon) {
            this.secondaryIcon = icon;
            return this;
        }
        
        public Builder thumbnail(final Icon icon) {
            this.thumbnail = icon;
            return this;
        }
        
        public Builder duration(final long duration) {
            this.duration = duration;
            return this;
        }
        
        public Builder type(final NotificationType type) {
            this.type = type;
            return this;
        }
        
        public Builder addButton(final NotificationButton button) {
            this.buttons.add(button);
            return this;
        }
        
        public Builder addButtons(final NotificationButton... buttons) {
            Collections.addAll(this.buttons, buttons);
            return this;
        }
        
        public Builder addButtons(final Collection<NotificationButton> buttons) {
            this.buttons.addAll(buttons);
            return this;
        }
        
        public Builder onClick(final Consumer<Notification> onClick) {
            this.onClick = onClick;
            return this;
        }
        
        public Notification build() {
            if (this.title == null) {
                throw new IllegalArgumentException("Missing title");
            }
            if (this.text == null && this.thumbnail == null) {
                throw new IllegalArgumentException("Missing text or thumbnail");
            }
            return new Notification(this.title, this.text, this.icon, this.secondaryIcon, this.thumbnail, this.duration, this.type, this.buttons, this.onClick);
        }
        
        public Notification buildAndPush() {
            final Notification notification = this.build();
            Laby.references().notificationController().push(notification);
            return notification;
        }
    }
    
    public static class NotificationButton
    {
        private final Component text;
        private final Component hoverText;
        private final Runnable action;
        private final boolean primary;
        
        private NotificationButton(final Component text, final Component hoverText, final Runnable action, final boolean primary) {
            this.text = text;
            this.hoverText = hoverText;
            this.action = action;
            this.primary = primary;
        }
        
        public Component text() {
            return this.text;
        }
        
        public Component hoverText() {
            return this.hoverText;
        }
        
        public Runnable action() {
            return this.action;
        }
        
        public boolean isPrimary() {
            return this.primary;
        }
        
        public static NotificationButton primary(final Component text, final Runnable action) {
            return new NotificationButton(text, null, action, true);
        }
        
        public static NotificationButton primary(final Component text, final Component hoverText, final Runnable action) {
            return new NotificationButton(text, hoverText, action, true);
        }
        
        public static NotificationButton of(final Component text, final Runnable action) {
            return new NotificationButton(text, null, action, false);
        }
        
        public static NotificationButton of(final Component text, final Component hoverText, final Runnable action) {
            return new NotificationButton(text, hoverText, action, false);
        }
    }
    
    public static class Type
    {
        public static NotificationType SYSTEM;
        public static NotificationType SOCIAL;
        
        static {
            Type.SYSTEM = DefaultNotificationType.create();
            Type.SOCIAL = SocialNotificationType.create();
        }
    }
}
