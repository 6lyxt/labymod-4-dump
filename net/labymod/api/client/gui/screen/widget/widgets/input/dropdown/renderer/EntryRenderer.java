// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;

public interface EntryRenderer<T>
{
    float getWidth(final T p0, final float p1);
    
    float getHeight(final T p0, final float p1);
    
    @NotNull
    default Widget createSelectedWidget(final T entry) {
        return this.createEntryWidget(entry);
    }
    
    @NotNull
    Widget createEntryWidget(final T p0);
}
