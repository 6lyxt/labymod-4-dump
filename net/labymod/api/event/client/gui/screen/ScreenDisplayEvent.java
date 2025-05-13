// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.Event;

public class ScreenDisplayEvent implements Event
{
    @Nullable
    private final ScreenInstance previousScreen;
    @Nullable
    private ScreenInstance screen;
    
    public ScreenDisplayEvent(@Nullable final ScreenWrapper previousScreen, @Nullable final ScreenWrapper screen) {
        this.previousScreen = previousScreen;
        this.screen = screen;
    }
    
    @Nullable
    public ScreenInstance getPreviousScreen() {
        return this.previousScreen;
    }
    
    @Nullable
    public ScreenInstance getScreen() {
        return this.screen;
    }
    
    public void setScreen(@Nullable final ScreenInstance screen) {
        this.screen = screen;
    }
}
