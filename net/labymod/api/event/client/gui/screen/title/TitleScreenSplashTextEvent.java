// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.title;

import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public final class TitleScreenSplashTextEvent extends DefaultCancellable implements Event
{
    private String splashText;
    
    public TitleScreenSplashTextEvent(final String splashText) {
        this.splashText = splashText;
    }
    
    public void setSplashText(final String splashText) {
        this.splashText = splashText;
    }
    
    public String getSplashText() {
        return this.splashText;
    }
}
