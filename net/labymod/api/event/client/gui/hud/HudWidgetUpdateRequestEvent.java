// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.hud;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;

public class HudWidgetUpdateRequestEvent extends HudWidgetEvent
{
    private final String reason;
    
    public HudWidgetUpdateRequestEvent(@NotNull final HudWidget<?> hudWidget, @NotNull final String reason) {
        super(hudWidget);
        this.reason = reason;
    }
    
    @Deprecated
    public HudWidgetUpdateRequestEvent(@NotNull final HudWidget<?> hudWidget) {
        this(hudWidget, "unknown");
    }
    
    @NotNull
    public String getReason() {
        return this.reason;
    }
}
