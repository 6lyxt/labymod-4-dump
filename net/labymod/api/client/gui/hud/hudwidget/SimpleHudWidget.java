// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;

public abstract class SimpleHudWidget<T extends HudWidgetConfig> extends HudWidget<T>
{
    protected SimpleHudWidget(final String id, final Class<T> configClass) {
        super(id, configClass);
    }
    
    @Override
    public final void updateSize(final HudWidgetWidget widget, final boolean isEditorContext, final HudSize size) {
        this.render(null, null, 0.0f, isEditorContext, size);
    }
}
