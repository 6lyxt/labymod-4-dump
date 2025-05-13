// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.theme;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.Event;

public class ThemeUpdateEvent implements Event
{
    private final Theme theme;
    private final Reason reason;
    
    public ThemeUpdateEvent(@NotNull final Theme theme, @NotNull final Reason reason) {
        Objects.requireNonNull(theme, "Theme cannot be null");
        Objects.requireNonNull(reason, "Reason cannot be null");
        this.theme = theme;
        this.reason = reason;
    }
    
    public Theme theme() {
        return this.theme;
    }
    
    public Reason reason() {
        return this.reason;
    }
    
    public static class Reason
    {
        private final String message;
        private final boolean changingDimensions;
        
        private Reason(@NotNull final String message, final boolean changingDimensions) {
            Objects.requireNonNull(message, "Message cannot be null");
            this.message = message;
            this.changingDimensions = changingDimensions;
        }
        
        public static Reason of(@NotNull final String message) {
            return new Reason(message, false);
        }
        
        public static Reason of(@NotNull final String message, final boolean changesDimensions) {
            return new Reason(message, changesDimensions);
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public boolean isChangingDimensions() {
            return this.changingDimensions;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Reason)) {
                return false;
            }
            final Reason reason = (Reason)o;
            return this.message.equals(reason.message) && this.changingDimensions == reason.changingDimensions;
        }
        
        @Override
        public int hashCode() {
            int result = this.message.hashCode();
            result = 31 * result + (this.changingDimensions ? 1 : 0);
            return result;
        }
    }
}
