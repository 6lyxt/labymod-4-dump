// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.widget;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.List;

@FunctionalInterface
public interface WidgetStorage
{
    void store(final List<Class<? extends Widget>> p0);
}
