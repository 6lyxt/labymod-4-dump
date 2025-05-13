// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.theme;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;

public class ThemeChangeEvent implements Event
{
    private final Phase phase;
    private final Theme oldTheme;
    private Theme newTheme;
    
    public ThemeChangeEvent(final Theme oldTheme, final Theme newTheme, @NotNull final Phase phase) {
        this.phase = phase;
        this.oldTheme = oldTheme;
        this.newTheme = newTheme;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public Theme oldTheme() {
        return this.oldTheme;
    }
    
    public Theme newTheme() {
        return this.newTheme;
    }
    
    public void setNewTheme(final Theme newTheme) {
        if (this.phase == Phase.POST) {
            throw new IllegalStateException("Cannot change theme in post phase");
        }
        this.newTheme = newTheme;
    }
}
