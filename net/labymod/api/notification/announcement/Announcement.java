// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification.announcement;

import org.jetbrains.annotations.ApiStatus;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.service.Identifiable;

public class Announcement implements Identifiable
{
    private final String id;
    private final Component text;
    private final byte priority;
    private boolean acknowledged;
    
    private Announcement(@NotNull final String id, @NotNull final Component text, final byte priority) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(text, "text");
        this.id = id;
        this.text = text;
        this.priority = priority;
    }
    
    public static Announcement create(@NotNull final String id, @NotNull final Component text) {
        return new Announcement(id, text, (byte)0);
    }
    
    public static Announcement create(@NotNull final String id, @NotNull final Component text, final byte priority) {
        return new Announcement(id, text, priority);
    }
    
    @NotNull
    @Override
    public String getId() {
        return this.id;
    }
    
    @NotNull
    public Component getText() {
        return this.text;
    }
    
    public byte getPriority() {
        return this.priority;
    }
    
    public boolean isAcknowledged() {
        return this.acknowledged;
    }
    
    @ApiStatus.Internal
    public void setAcknowledged(final boolean acknowledged) {
        this.acknowledged = acknowledged;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof final Announcement that) {
            return this.priority == that.priority && this.acknowledged == that.acknowledged && this.id.equals(that.id) && this.text.equals(that.text);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + this.text.hashCode();
        result = 31 * result + this.priority;
        result = 31 * result + (this.acknowledged ? 1 : 0);
        return result;
    }
}
