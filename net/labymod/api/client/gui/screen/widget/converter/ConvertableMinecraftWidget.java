// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public interface ConvertableMinecraftWidget<K extends AbstractWidget<?>>
{
    WidgetWatcher<K> getWatcher();
}
