// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.Event;

public class ScreenOpenEvent implements Event
{
    private ScreenInstance screen;
    
    public ScreenOpenEvent(final ScreenInstance screen) {
        this.screen = screen;
    }
    
    @Nullable
    public ScreenInstance getScreen() {
        return this.screen;
    }
    
    @Deprecated
    @Nullable
    public ScreenInstance getPreviousScreen() {
        return null;
    }
    
    @Deprecated
    public void setScreen(final ScreenInstance screen) {
        this.screen = screen;
    }
}
