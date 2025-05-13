// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.hud;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;

public class HudWidgetDestroyedEvent extends HudWidgetEvent
{
    public HudWidgetDestroyedEvent(@NotNull final HudWidget<?> hudWidget) {
        super(hudWidget);
    }
}
