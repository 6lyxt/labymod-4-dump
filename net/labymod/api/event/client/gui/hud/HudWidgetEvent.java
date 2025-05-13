// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.hud;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.event.Event;

public class HudWidgetEvent implements Event
{
    private final HudWidget<?> hudWidget;
    
    public HudWidgetEvent(@NotNull final HudWidget<?> hudWidget) {
        this.hudWidget = hudWidget;
    }
    
    public HudWidget<?> hudWidget() {
        return this.hudWidget;
    }
}
